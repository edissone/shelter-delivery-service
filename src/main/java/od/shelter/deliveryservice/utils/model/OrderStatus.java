package od.shelter.deliveryservice.utils.model;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum OrderStatus {
    CREATED((short) 100), ASSIGNED((short) 110),
    CONFIRM((short) 200), PREPARING((short) 210),
    READY_DEL((short) 300), READY_SELF((short) 310),
    ASSIGNED_DEL((short) 400),
    GOING((short) 410), ARRIVED((short) 420), DELIVERED((short) 500),
    GOT_SELF((short) 510),
    DECLINED_CUSTOMER((short) -100), DECLINED_SUPPLIER((short) -200),
    DECLINED_DELIVER((short) -300);

    private final short code;

    OrderStatus(short code) {
        this.code = code;
    }

    public static List<OrderStatus> active() {
        return Stream.of(
                OrderStatus.CREATED, OrderStatus.ASSIGNED, OrderStatus.CONFIRM,
                OrderStatus.PREPARING, OrderStatus.READY_DEL, OrderStatus.ASSIGNED_DEL, OrderStatus.GOING,
                OrderStatus.READY_SELF, OrderStatus.ARRIVED
        ).sorted(Comparator.comparing(OrderStatus::getCode)).collect(Collectors.toList());
    }
}
