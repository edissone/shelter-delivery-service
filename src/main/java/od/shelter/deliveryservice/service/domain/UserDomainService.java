package od.shelter.deliveryservice.service.domain;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;

public interface UserDomainService {
    User create(UserDTO dto);

    User get(String tgID);

    User delete(String tgID);

    User update(String id, UserDTO dto);
}
