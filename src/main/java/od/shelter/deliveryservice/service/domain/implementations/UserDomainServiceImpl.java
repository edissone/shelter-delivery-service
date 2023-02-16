package od.shelter.deliveryservice.service.domain.implementations;

import lombok.extern.slf4j.Slf4j;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dao.repository.UserRepository;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.exception.AlreadyExistsException;
import od.shelter.deliveryservice.exception.NotFoundException;
import od.shelter.deliveryservice.service.domain.UserDomainService;
import od.shelter.deliveryservice.utils.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDomainServiceImpl implements UserDomainService {
    private final UserRepository repository;

    @Autowired
    public UserDomainServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(UserDTO dto) {
        if (repository.existsByTgID(dto.getTgID())) {
            throw new AlreadyExistsException("tg_id", dto.getTgID(), User.class);
        }

        return createUser(dto);
    }

    @Override
    public User get(String tgID) {
        return repository.findByTgID(tgID).orElseThrow(() -> new NotFoundException("TG_ID", tgID, User.class));
    }

    @Override
    public User delete(String tgID) {
        var deletable = repository.findByTgID(tgID);
        if (deletable.isEmpty()) {
            throw new NotFoundException("TG_ID", tgID, User.class);
        }
        repository.delete(deletable.get());
        return deletable.get();
    }

    @Override
    public User update(String id, UserDTO dto) {
        var updatable = this.get(id);

        updatable.setFullName(dto.getFullName() != null ? dto.getFullName() : updatable.getFullName());
        updatable.setAddress(dto.getAddress() != null ? dto.getAddress() : updatable.getAddress());
        updatable.setPhone(dto.getPhone() != null ? dto.getPhone() : updatable.getPhone());

        return repository.save(updatable);
    }

    @Override
    public List<User> fetch(Role role) {
        return repository.findAllByRole(role);
    }

    @Override
    public User addDeliver(UserDTO dto) {
        final var exists = repository.findByTgID(dto.getTgID());
        if (exists.isPresent()) {
            final var updateable = exists.get();
            if (updateable.getRole() == Role.DELIVER) {
                throw new AlreadyExistsException("TG_ID/Role",
                        updateable.getTgID() + "/" + updateable.getRole(), User.class);
            }
            updateable.setRole(Role.DELIVER);
            return repository.save(updateable);
        } else {
            return createUser(dto);
        }
    }

    @Override
    public User removeDeliver(String tgID) {
        final var exists = repository.findByTgID(tgID);
        User updatable = null;
        if (exists.isPresent() && exists.get().getRole() == Role.DELIVER) {
            updatable = exists.get();
            updatable.setRole(Role.CUSTOMER);
        } else {
            throw new NotFoundException("TG_ID", tgID, User.class);
        }
        return repository.save(updatable);
    }

    private User createUser(UserDTO dto) {
        var insertable = User.builder()
                .tgID(dto.getTgID())
                .phone(dto.getPhone())
                .fullName(dto.getFullName())
                .role(dto.getRole())
                .build();
        return repository.save(insertable);
    }
}
