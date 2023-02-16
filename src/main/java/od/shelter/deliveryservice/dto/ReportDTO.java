package od.shelter.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
public class ReportDTO {
    private LocalDate date;
    private List<ReportItem> completed;
    private List<ReportItem> declined;
    private List<ReportItem> other;
    @JsonProperty("total_amount")
    private double totalAmount;
}
