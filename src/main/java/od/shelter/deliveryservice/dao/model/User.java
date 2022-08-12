package od.shelter.deliveryservice.dao.model;

import lombok.*;
import od.shelter.deliveryservice.utils.model.Role;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tg_id", length = 12)
    @EqualsAndHashCode.Exclude
    private String ID;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name="fullname", length = 60)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "owner", fetch = FetchType.LAZY
    )
    private List<Order> ordersOwned;

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "supplier", fetch = FetchType.LAZY
    )
    private List<Order> ordersSupplied;

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            mappedBy = "delivery", fetch = FetchType.LAZY
    )
    private List<Order> ordersDelivered;
}
