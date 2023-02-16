package od.shelter.deliveryservice.service.domain;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.utils.model.Role;

import java.util.List;

public interface UserDomainService {
    User create(UserDTO dto);

    User get(String tgID);

    User delete(String tgID);

    User update(String id, UserDTO dto);

    List<User> fetch(Role role);

    User addDeliver(UserDTO dto);

    User removeDeliver(String tgID);
}
