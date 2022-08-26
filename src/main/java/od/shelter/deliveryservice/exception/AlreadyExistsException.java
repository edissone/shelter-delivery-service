package od.shelter.deliveryservice.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException{
    private final String field;
    private final String value;
    private final Class<?> entityClass;

    public AlreadyExistsException(String field, Object value, Class<?> entityClass) {
        super(
                String.format("%s entity with %s:%s already exists",
                        entityClass.getSimpleName(), field, value)
        );
        this.field = field;
        this.value = String.valueOf(value);
        this.entityClass = entityClass;
    }
}
