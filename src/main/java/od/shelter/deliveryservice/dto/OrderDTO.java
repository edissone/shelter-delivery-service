package od.shelter.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import od.shelter.deliveryservice.dao.model.jsonb.DeliveryInfo;
import od.shelter.deliveryservice.dao.model.jsonb.OrderLog;
import od.shelter.deliveryservice.dao.model.jsonb.PositionStub;
import od.shelter.deliveryservice.utils.model.DeliveryType;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import od.shelter.deliveryservice.utils.model.PaymentType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    @JsonProperty("owner_id")
    private String ownerID;
    @JsonProperty("supplier_id")
    private String supplierID;
    @JsonProperty("delivery_id")
    private String deliveryID;
    private String notes;
    private Double amount;
    @JsonProperty("payback_from")
    private Double paybackFrom;
    private Double payback;
    @JsonProperty("payment_code")
    private String paymentCode;
    @JsonProperty("payment_type")
    private PaymentType paymentType;
    @JsonProperty("delivery_type")
    private DeliveryType deliveryType;
    @JsonProperty("delivery_info")
    private DeliveryInfo deliveryInfo;
    private List<PositionStub> positions;
    @EqualsAndHashCode.Exclude
    private List<OrderLog> logs;
    @JsonProperty("status")
    private OrderStatus currentStatus;
}
