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
public class DeliveryInfo {
    private String address;
    private String fullName;
    private String phone;
    private String notes;
}
