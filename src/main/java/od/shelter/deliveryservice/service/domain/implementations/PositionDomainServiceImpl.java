package od.shelter.deliveryservice.service.domain.implementations;

import od.shelter.deliveryservice.dao.model.Position;
import od.shelter.deliveryservice.dao.repository.PositionRepository;
import od.shelter.deliveryservice.service.domain.PositionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PositionDomainServiceImpl implements PositionDomainService {
    private final PositionRepository repository;

    @Autowired
    public PositionDomainServiceImpl(PositionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<Long, Position> findAllIn(List<Long> IDs) {
        return repository.findAllByIdIn(IDs).stream().collect(Collectors.toMap(Position::getId, Function.identity()));
    }

    @Override
    public List<Position> fetch(String category) {
        return category == null ? repository.findAll() : repository.findAllByCategory(category);
    }
}
