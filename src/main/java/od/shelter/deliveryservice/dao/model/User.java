package od.shelter.deliveryservice.dao.model;

import lombok.*;
import od.shelter.deliveryservice.utils.model.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "users")
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "tg_id", length = 12)
    private String tgID;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name="full_name", length = 60)
    private String fullName;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ToString.Exclude
    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "owner", fetch = FetchType.LAZY
    )
    private List<Order> ordersOwned;

    @ToString.Exclude
    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "supplier", fetch = FetchType.LAZY
    )
    private List<Order> ordersSupplied;

    @ToString.Exclude
    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "delivery", fetch = FetchType.LAZY
    )
    private List<Order> ordersDelivered;
}
