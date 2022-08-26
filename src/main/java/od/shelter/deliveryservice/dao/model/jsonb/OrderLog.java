package od.shelter.deliveryservice.dao.model.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import od.shelter.deliveryservice.utils.converter.StatusConverter;
import od.shelter.deliveryservice.utils.model.OrderStatus;

import javax.persistence.Convert;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderLog {
    @Convert(converter = StatusConverter.class)
    private OrderStatus status;
    private LocalDateTime date;
}
