package od.shelter.deliveryservice.dao.repository;

import od.shelter.deliveryservice.dao.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByIdIn(List<Long> IDs);
    List<Position> findAllByCategory(String category);
}
