package od.shelter.deliveryservice.exception;

import lombok.Getter;
import od.shelter.deliveryservice.utils.model.OrderStatus;

@Getter
public class InvalidStateException extends RuntimeException {
    private final String ID;
    private final Class<?> entityClass;
    private final Class<?> stateType;
    private final String stage;
    private final OrderStatus status;

    public InvalidStateException(Object id, Class<?> entityClass, Class<?> stateType, OrderStatus status, String stage) {
        super(
                String.format("Invalid state of type:%s with ID:%s on stage:%s with status = %s:%s",
                        entityClass.getSimpleName(), id, stage, stateType.getSimpleName(), status.name()
                )
        );
        this.ID = String.valueOf(id);
        this.entityClass = entityClass;
        this.stateType = stateType;
        this.stage = stage;
        this.status = status;
    }
}
