package od.shelter.deliveryservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String field;
    private final String value;
    private final Class<?> entityClass;

    public NotFoundException(String field, Object value, Class<?> entityClass) {
        super(
                String.format("%s entity with %s:%s not found",
                        entityClass.getSimpleName(), field, value)
        );
        this.field = field;
        this.value = String.valueOf(value);
        this.entityClass = entityClass;
    }
}
