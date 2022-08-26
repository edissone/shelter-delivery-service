package od.shelter.deliveryservice.utils.mapper.implementations;

import od.shelter.deliveryservice.dao.model.Position;
import od.shelter.deliveryservice.dto.PositionDTO;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper implements EntityMapper<Position, PositionDTO> {
    @Override
    public PositionDTO dto(Position entity) {
        return PositionDTO.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .description(entity.getDescription())
                .name(entity.getName())
                .price(entity.getPrice())
                .weight(entity.getWeight())
                .image(entity.getImage())
                .build();
    }
}
