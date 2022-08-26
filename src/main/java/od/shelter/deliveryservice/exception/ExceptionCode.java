package od.shelter.deliveryservice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_FOUND((short) 4000),
    INVALID_STATE((short) 4010),
    ACTIVE_ORDERS_EXISTS((short) 4020),
    PERMISSION_DENIED((short) 4030),
    UNASSIGNED_ORDER((short) 4040),
    ALREADY_EXISTS((short) 4050),
    INVALID_COMPUTATION((short) 5000);

    private final short code;

    ExceptionCode(short code) {
        this.code = code;
    }
}
