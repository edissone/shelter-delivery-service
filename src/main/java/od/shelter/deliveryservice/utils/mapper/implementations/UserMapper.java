package od.shelter.deliveryservice.utils.mapper.implementations;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, UserDTO> {
    @Override
    public UserDTO dto(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .tgID(entity.getTgID())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .address(entity.getAddress())
                .build();
    }
}
