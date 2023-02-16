package od.shelter.deliveryservice.service.implementations;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.jsonb.PositionStub;
import od.shelter.deliveryservice.dao.repository.OrderRepository;
import od.shelter.deliveryservice.dto.ReportDTO;
import od.shelter.deliveryservice.dto.ReportItem;
import od.shelter.deliveryservice.service.ReportService;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import od.shelter.deliveryservice.utils.model.ReportOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final OrderRepository repository;

    @Autowired
    public ReportServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<ReportDTO> getReport(ReportOptions options) {
        final var orders = repository.findAll();

        final var ordersByDates = orders.stream()
                .collect(Collectors.groupingBy(o -> o.getCurrentStatus().getDate().toLocalDate()));

        return ordersByDates.keySet().stream()
                .filter(options.getDateFilter())
                .map(date -> Pair.of(date, ordersByDates.get(date)))
                .map(this::createReport)
                .toList();
    }

    private ReportDTO createReport(Pair<LocalDate, List<Order>> ordersByDate) {
        final var items = ordersByDate.getSecond()
                .stream()
                .map(this::createReportItem)
                .collect(Collectors.groupingBy(ReportItem::getState));

        final var report = ReportDTO.builder()
                .date(ordersByDate.getFirst())
                .completed(items.get((byte) 1) != null ? items.get((byte) 1) : List.of())
                .other(items.get((byte) 0) != null ? items.get((byte) 0) : List.of())
                .declined(items.get((byte) -1) != null ? items.get((byte) -1) : List.of());

        Optional.ofNullable(items.get((byte) 1)).flatMap(ris -> ris.stream()
                .map(ReportItem::getAmount)
                .reduce(Double::sum)).ifPresent(report::totalAmount);

        return report.build();
    }

    private ReportItem createReportItem(Order order) {
        final var currentStatus = order.getCurrentStatus().getStatus();
        final var item = ReportItem.builder()
                .number(order.getId())
                .amount(order.getAmount())
                .paymentType(order.getPaymentType())
                .paymentCode(order.getPaymentCode())
                .paybackFrom(order.getPaybackFrom() != null ? order.getPaybackFrom() : null)
                .payback(order.getPaybackFrom() != null ? order.getPaybackFrom() - order.getAmount() : null)
                .deliveryType(order.getDeliveryType())
                .deliveryAddress(order.getDeliveryInfo().getAddress())
                .status(currentStatus)
                .date(order.getCurrentStatus().getDate().toLocalTime());

        final var positions = order.getPositions()
                .stream()
                .collect(Collectors.toMap(PositionStub::getName, PositionStub::getCount));

        item.positions(positions);

        Optional.ofNullable(order.getSupplier()).ifPresent(s -> item.supplier(s.getFullName()));
        Optional.ofNullable(order.getDelivery()).ifPresent(d -> item.deliver(d.getFullName()));

        if (OrderStatus.completed().contains(currentStatus)) {
            item.state((byte) 1);
        } else if (OrderStatus.active().contains(currentStatus)) {
            item.state((byte) 0);
        } else {
            item.state((byte) -1);
        }

        return item.build();
    }

}
