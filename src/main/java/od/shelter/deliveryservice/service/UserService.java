package od.shelter.deliveryservice.service;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;

public interface UserService {
    User create(UserDTO dto);

    User delete(String id);

    User get(String id);

    User update(String id, UserDTO dto);
}
