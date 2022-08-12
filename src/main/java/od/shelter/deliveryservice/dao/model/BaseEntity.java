package od.shelter.deliveryservice.dao.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class BaseEntity {
}
