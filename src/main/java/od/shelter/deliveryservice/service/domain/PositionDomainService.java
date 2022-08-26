package od.shelter.deliveryservice.service.domain;

import od.shelter.deliveryservice.dao.model.Position;

import java.util.List;
import java.util.Map;

public interface PositionDomainService {
    Map<Long, Position> findAllIn(List<Long> IDs);
    List<Position> fetch(String category);
}
