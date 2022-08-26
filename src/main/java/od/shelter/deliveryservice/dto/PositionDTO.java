package od.shelter.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PositionDTO {
    private long id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String weight;
    private String image;
}
