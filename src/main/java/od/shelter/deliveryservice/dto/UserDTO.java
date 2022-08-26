package od.shelter.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import od.shelter.deliveryservice.utils.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    @JsonProperty("tg_id")
    private String tgID;
    private String phone;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    private Role role;
}
