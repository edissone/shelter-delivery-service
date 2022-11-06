package od.shelter.deliveryservice.dao.repository;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByPaymentCode(String code);

    List<Order> findAllByOwnerTgID(String ownerID);

    List<Order> findByDelivery(User delivery);

    List<Order> findBySupplier(User delivery);
}
