package od.shelter.deliveryservice.dao.model;

import lombok.*;
import od.shelter.deliveryservice.dao.model.jsonb.DeliveryInfo;
import od.shelter.deliveryservice.dao.model.jsonb.OrderLog;
import od.shelter.deliveryservice.dao.model.jsonb.PositionStub;
import od.shelter.deliveryservice.utils.model.DeliveryType;
import od.shelter.deliveryservice.utils.model.PaymentType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "orders")
@ToString
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "notes")
    private String notes;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payback_from")
    private Double paybackFrom;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "status")
    private Short status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Type(type = "jsonb")
    @Column(name = "positions")
    private List<PositionStub> positions;

    @Type(type = "jsonb")
    @Column(name = "delivery_info")
    private DeliveryInfo deliveryInfo;

    @Type(type = "jsonb")
    @Column(name = "order_logs")
    private List<OrderLog> logs;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_owner_id", referencedColumnName = "tg_id")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", referencedColumnName = "tg_id")
    private User supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id", referencedColumnName = "tg_id")
    private User delivery;
}
