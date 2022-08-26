package od.shelter.deliveryservice.service;

import od.shelter.deliveryservice.dao.model.Position;

import java.util.List;

public interface PositionService {
    List<Position> fetch(String category);
}
