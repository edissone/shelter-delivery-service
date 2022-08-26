package od.shelter.deliveryservice.utils.converter;

import od.shelter.deliveryservice.utils.model.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class StatusConverter implements AttributeConverter<OrderStatus, Short> {
    @Override
    public Short convertToDatabaseColumn(OrderStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Short dbData) {
        return Arrays.stream(OrderStatus.values())
                .filter(item -> item.getCode() == dbData)
                .findFirst()
                .orElseThrow(RuntimeException::new);    // todo: generic exception!
    }
}
