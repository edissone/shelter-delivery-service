package od.shelter.deliveryservice.dao.model.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PositionStub {
    private Long id;
    private int count;
    private double price;
}
