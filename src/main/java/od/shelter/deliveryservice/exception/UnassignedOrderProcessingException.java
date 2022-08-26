package od.shelter.deliveryservice.exception;

import lombok.Getter;
import od.shelter.deliveryservice.utils.model.Role;

@Getter
public class UnassignedOrderProcessingException extends RuntimeException{
    private final Long id;

    public UnassignedOrderProcessingException(Long id, Role role) {
        super("Order with id:" + id + " cannot continue process without assigned role: " + role.name());
        this.id = id;
    }
}
