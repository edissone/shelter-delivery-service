package od.shelter.deliveryservice.dao.repository;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.utils.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTgID(String tgID);
    List<User> findAllByRole(Role role);
    boolean existsByTgID(String tgID);
}
