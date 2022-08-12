package od.shelter.deliveryservice.dao.model.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import od.shelter.deliveryservice.utils.model.OrderStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderLog {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime date;
}
