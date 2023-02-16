package od.shelter.deliveryservice.service;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.utils.model.Role;

import java.util.List;

public interface UserService {
    User create(UserDTO dto);

    User delete(String id);

    User get(String id);

    User update(String id, UserDTO dto);

    List<User> fetch(Role role);

    User addDeliver(UserDTO dto);

    User removeDeliver(String tgID);
}
