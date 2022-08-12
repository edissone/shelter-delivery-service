package od.shelter.deliveryservice.utils.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED(100), CONFIRMED(200), PREPARING(210) , GOING(300), DELIVERED(400);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }
}
