package od.shelter.deliveryservice.dao.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "positions")
@ToString
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "p_name", length = 40, unique = true)
    private String name;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "weight", length = 30)
    private String weight;

    @Column(name = "image", length = 50)
    private String image;
}
