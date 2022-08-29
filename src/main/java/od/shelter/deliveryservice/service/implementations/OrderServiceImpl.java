package od.shelter.deliveryservice.service.implementations;

import lombok.extern.slf4j.Slf4j;
import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.Position;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dao.model.jsonb.PositionStub;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.exception.ActiveOrdersExistException;
import od.shelter.deliveryservice.exception.PermissionDeniedException;
import od.shelter.deliveryservice.service.OrderService;
import od.shelter.deliveryservice.service.domain.OrderDomainService;
import od.shelter.deliveryservice.service.domain.PositionDomainService;
import od.shelter.deliveryservice.service.domain.UserDomainService;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import od.shelter.deliveryservice.utils.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private static final String LOG_FORMAT = "{}: {}";
    private final OrderDomainService orderDomainService;
    private final UserDomainService userDomainService;
    private final PositionDomainService positionDomainService;

    @Autowired
    public OrderServiceImpl(OrderDomainService orderDomainService, UserDomainService userDomainService,
                            PositionDomainService positionDomainService) {
        this.orderDomainService = orderDomainService;
        this.userDomainService = userDomainService;
        this.positionDomainService = positionDomainService;
    }

    @Override
    public Order create(OrderDTO dto) {
        final var orderOwner = userDomainService.get(dto.getOwnerID());
        log.info(LOG_FORMAT, dto.getOwnerID(), "CREATE NEW ORDER");
        verifyActiveOrders(orderOwner);
        populatePositionStubs(findAllByStubs(dto.getPositions().stream()), dto.getPositions());
        return orderDomainService.create(orderOwner, dto);
    }

    @Override
    public Order get(Long orderID) {
        log.info("GET ORDER BY ID: {}", orderID);
        return orderDomainService.get(orderID);
    }

    @Override
    public List<Order> findOwners(String ownerID) {
        return orderDomainService.findOwners(ownerID);
    }

    @Override
    public Order assign(Long orderID, String userTGID) {
        final var assigned = userDomainService.get(userTGID);
        return orderDomainService.assign(orderID, assigned);
    }

    @Override
    public Order confirm(Long orderID, String userTGID) {
        var assigned = verifyUser(orderID, userTGID, Role.SUPPLIER);
        return orderDomainService.confirm(orderID, assigned);
    }

    @Override
    public Order ready(Long orderID, String userTGID) {
        verifyUser(orderID, userTGID, Role.SUPPLIER);
        return orderDomainService.ready(orderID);
    }

    @Override
    public Order going(Long orderID, String userTGID) {
        verifyUser(orderID, userTGID, Role.DELIVER);
        return orderDomainService.going(orderID);
    }

    @Override
    public Order delivered(Long orderID, String userTGID) {
        verifyUser(orderID, userTGID, Role.DELIVER);
        return orderDomainService.delivered(orderID);
    }

    @Override
    public Order decline(Long orderID, String userTGID) {
        final var decliner = userDomainService.get(userTGID);
        return orderDomainService.decline(orderID, decliner);
    }

    @Override
    public List<Order> fetch(OrderStatus status, boolean active) {
        return orderDomainService.fetch(status);
    }

    private void populatePositionStubs(Map<Long, Position> map, List<PositionStub> list) {
        list.forEach(
                ps -> Optional.ofNullable(map.get(ps.getId()))
                        .ifPresent(
                                p -> {
                                    ps.setName(p.getName());
                                    ps.setPrice(p.getPrice());
                                }
                        )
        );
    }

    private User verifyUser(Long orderID, String userTGID, Role requiredRole) {
        final var assign = userDomainService.get(userTGID);
        if (assign.getRole() != requiredRole) {
            throw new PermissionDeniedException(assign.getTgID(), assign.getRole(), orderID);
        }
        return assign;
    }

    private void verifyActiveOrders(User orderOwner) {
        log.info(LOG_FORMAT, orderOwner.getTgID(), "VERIFYING EXIST ACTIVE ORDERS");
        final var activeStates = OrderStatus.active();
        final var activeOrders = orderOwner.getOrdersOwned()
                .stream()
                .filter(o -> activeStates.contains(o.getCurrentStatus().getStatus()))
                .toList();
        if (!activeOrders.isEmpty()) {
            throw new ActiveOrdersExistException(orderOwner.getTgID());
        }
    }

    private Map<Long, Position> findAllByStubs(Stream<PositionStub> stubs){
        final var IDs = stubs
                .map(PositionStub::getId)
                .toList();
        return positionDomainService.findAllIn(IDs);
    }

}
