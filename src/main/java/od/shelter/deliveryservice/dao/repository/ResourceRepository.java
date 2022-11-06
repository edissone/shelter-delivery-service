package od.shelter.deliveryservice.dao.repository;

import od.shelter.deliveryservice.dao.model.ResourceParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface ResourceRepository extends JpaRepository<ResourceParam, Long> {
}
