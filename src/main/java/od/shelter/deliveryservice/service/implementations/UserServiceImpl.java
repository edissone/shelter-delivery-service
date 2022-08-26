package od.shelter.deliveryservice.service.implementations;

import lombok.extern.slf4j.Slf4j;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.service.UserService;
import od.shelter.deliveryservice.service.domain.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String LOG_FORMAT = "{}: {}";
    private final UserDomainService userDomainService;

    @Autowired
    public UserServiceImpl(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @Override
    public User create(UserDTO dto) {
        log.info(LOG_FORMAT, dto.getTgID(), "CREATE NEW USER");
        return userDomainService.create(dto);
    }

    @Override
    public User delete(String tgID) {
        log.info(LOG_FORMAT, tgID, "DELETE USER");
        return userDomainService.delete(tgID);
    }

    @Override
    public User get(String tgID) {
        return userDomainService.get(tgID);
    }

    @Override
    public User update(String id, UserDTO dto) {
        return userDomainService.update(id, dto);
    }
}
