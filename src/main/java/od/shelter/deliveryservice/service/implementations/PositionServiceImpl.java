package od.shelter.deliveryservice.service.implementations;

import od.shelter.deliveryservice.dao.model.Position;
import od.shelter.deliveryservice.service.PositionService;
import od.shelter.deliveryservice.service.domain.PositionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionDomainService positionDomainService;

    @Autowired
    public PositionServiceImpl(PositionDomainService positionDomainService) {
        this.positionDomainService = positionDomainService;
    }

    @Override
    public List<Position> fetch(String category) {
        return positionDomainService.fetch(category);
    }
}
