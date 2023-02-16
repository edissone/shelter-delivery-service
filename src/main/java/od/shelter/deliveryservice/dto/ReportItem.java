package od.shelter.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import od.shelter.deliveryservice.utils.model.DeliveryType;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import od.shelter.deliveryservice.utils.model.PaymentType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
public class ReportItem {
    private long number;
    private String supplier;
    private String deliver;
    private double amount;
    @JsonProperty("payment_type")
    private PaymentType paymentType;
    @JsonProperty("payment_code")
    private String paymentCode;
    @JsonProperty("payback_from")
    private Double paybackFrom;
    private Double payback;
    @JsonProperty("delivery_type")
    private DeliveryType deliveryType;
    @JsonProperty("delivery_address")
    private String deliveryAddress;
    private Map<String, Integer> positions;
    private OrderStatus status;
    private LocalTime date;
    @JsonIgnore
    private byte state;
}
