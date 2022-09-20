package od.shelter.deliveryservice.dao.model.jsonb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
public class DeliveryInfo {
    private String address;
    @JsonProperty("full_name")
    private String fullName;
    private String phone;
    private String notes;
}
