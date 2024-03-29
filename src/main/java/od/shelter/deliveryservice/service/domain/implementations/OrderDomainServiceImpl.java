package od.shelter.deliveryservice.service.domain.implementations;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dao.model.jsonb.OrderLog;
import od.shelter.deliveryservice.dao.model.jsonb.PositionStub;
import od.shelter.deliveryservice.dao.repository.OrderRepository;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.exception.*;
import od.shelter.deliveryservice.service.domain.OrderDomainService;
import od.shelter.deliveryservice.utils.generator.Generator;
import od.shelter.deliveryservice.utils.model.DeliveryType;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import od.shelter.deliveryservice.utils.model.PaymentType;
import od.shelter.deliveryservice.utils.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class OrderDomainServiceImpl implements OrderDomainService {
    private final OrderRepository repository;
    private final Generator codeGenerator;

    @Autowired
    public OrderDomainServiceImpl(OrderRepository repository, Generator codeGenerator) {
        this.repository = repository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public Order create(User orderOwner, OrderDTO dto) {
        final var order = Order.builder().notes(dto.getNotes()).owner(orderOwner).deliveryType(dto.getDeliveryType()).paymentType(dto.getPaymentType()).positions(dto.getPositions()).deliveryInfo(dto.getDeliveryInfo()).log(OrderLog.builder().status(OrderStatus.CREATED).date(LocalDateTime.now()).build());

        processPayment(order, dto);
        return repository.save(order.build());
    }

    @Override
    public Order get(Long orderID) {
        return repository.findById(orderID).orElseThrow(() -> new NotFoundException("ID", orderID, Order.class));
    }

    @Override
    public List<Order> findOwners(String ownerID) {
        return repository.findAllByOwnerTgID(ownerID);
    }

    @Override
    @Transactional
    public Order assign(Long orderID, User user) {
        final var order = this.get(orderID);
        assignByRole(user, order);
        return repository.save(order);
    }

    @Override
    @Transactional
    public Order confirm(Long orderID, User assignedSupplier) {
        final var order = this.get(orderID);
        final var statusCode = order.getCurrentStatus().getStatus().getCode();
        final var prev = OrderStatus.ASSIGNED.getCode();

        verifyState(order, statusCode, List.of(prev), "BEFORE CONFIRMATION", true);

        if (!order.getSupplier().equals(assignedSupplier)) {
            throw new PermissionDeniedException(assignedSupplier.getTgID(), assignedSupplier.getRole(), orderID);
        }

        order.log(OrderStatus.CONFIRM);
        order.log(OrderStatus.PREPARING);

        return repository.save(order);
    }

    @Override
    public Order decline(Long orderID, User decliner) {
        final var order = this.get(orderID);
        final var statusCode = order.getCurrentStatus().getStatus().getCode();
        final var active = OrderStatus.active().stream().map(OrderStatus::getCode).toList();

        verifyState(order, statusCode, active, "DECLINING", true);
        logByRole(decliner, order);

        return repository.save(order);
    }

    private void assignByRole(User user, Order order) {
        if (user.getRole() == Role.SUPPLIER && order.getSupplier() == null) {
            order.setSupplier(user);
            order.log(OrderStatus.ASSIGNED);
            if (order.getPaymentType() == PaymentType.CASH) {
                order.log(OrderStatus.PREPARING);
            }
        } else if (user.getRole() == Role.DELIVER && order.getDelivery() == null) {
            order.setDelivery(user);
            order.log(OrderStatus.ASSIGNED_DEL);
        } else {
            throw new UnsupportedOperationException("This operation is not supported: assign with role: " + user.getRole());
        }
    }

    private void logByRole(User decliner, Order order) {
        if (decliner.getRole() == Role.CUSTOMER) {
            order.log(OrderStatus.DECLINED_CUSTOMER);
        } else if (decliner.getRole() == Role.SUPPLIER) {
            order.log(OrderStatus.DECLINED_SUPPLIER);
        } else if (decliner.getRole() == Role.DELIVER) {
            order.log(OrderStatus.DECLINED_DELIVER);
        } else {
            throw new UnsupportedOperationException("This operation is not supported: assign with role: " + decliner.getRole());
        }
    }

    @Override
    public List<Order> fetch(OrderStatus status, boolean active) {
        Predicate<Order> predicate = active ? o -> OrderStatus.active().contains(o.getCurrentStatus().getStatus()) : o -> o.getCurrentStatus().getStatus() == status;
        return status == null && !active ? repository.findAll() : repository.findAll().stream().filter(predicate).toList();
    }

    @Override
    public Order ready(Long orderID) {
        final var order = this.get(orderID);
        order.log(order.getDeliveryType() == DeliveryType.DELIVERY ? OrderStatus.READY_DEL : OrderStatus.READY_SELF);
        return repository.save(order);
    }

    @Override
    @Transactional
    public Order going(Long orderID) {
        final var order = this.get(orderID);
        if (order.getDelivery() == null) {
            throw new UnassignedOrderProcessingException(orderID, Role.DELIVER);
        }
        order.log(OrderStatus.GOING);
        return repository.save(order);
    }

    @Override
    public Order arrived(Long orderID) {
        final var order = this.get(orderID);
        order.log(OrderStatus.ARRIVED);
        return repository.save(order);
    }

    @Override
    public Order delivered(Long orderID) {
        final var order = this.get(orderID);
        order.log(OrderStatus.DELIVERED);
        return repository.save(order);
    }

    @Override
    public Order gotSelf(Long orderID) {
        final var order = this.get(orderID);
        order.log(OrderStatus.GOT_SELF);
        return repository.save(order);
    }

    @Override
    public Order fetchAssigned(User assigned, boolean active) {
        var activeStatuses = OrderStatus.active();
        final var orders = assigned.getRole() == Role.DELIVER ? repository.findByDelivery(assigned) :
                repository.findBySupplier(assigned);
        if (!orders.isEmpty()) {
            if (active) {
                return orders.stream().filter(o -> activeStatuses.contains(o.getCurrentStatus().getStatus())).findFirst().orElseThrow(
                        () -> new NotFoundException("ASSIGNED/active", assigned.getId() + '/' + "true", Order.class)
                );
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            throw new NotFoundException("ASSIGNED/active", assigned.getId() + "/" + "true", Order.class);
        }
    }

    private void processPayment(Order.OrderBuilder order, OrderDTO dto) {
        final var amount = processAmount(dto.getPositions().stream());
        order.amount(amount);
        if (dto.getPaymentType() == PaymentType.CARD) {
            order.paymentCode(createPaymentCode());
        } else {
            order.paybackFrom(dto.getPaybackFrom());
        }
    }

    private String createPaymentCode() {
        final var code = codeGenerator.generate();
        final var exists = repository.existsByPaymentCode(code);
        return exists ? createPaymentCode() : code;
    }

    private double processAmount(Stream<PositionStub> psStream) {
        return psStream.map(ps -> ps.getCount() * ps.getPrice()).reduce(Double::sum).orElseThrow(() -> new InvalidComputationException("Amount process was failed :("));
    }

    private void verifyState(Order order, short code, List<Short> statuses, String stage, boolean present) {
        if (present != statuses.contains(code)) {
            throw new InvalidStateException(order.getId(), Order.class, OrderStatus.class, order.getCurrentStatus().getStatus(), stage);
        }
    }
}
