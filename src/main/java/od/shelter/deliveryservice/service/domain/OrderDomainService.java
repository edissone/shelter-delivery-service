package od.shelter.deliveryservice.service.domain;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.utils.model.OrderStatus;

import java.util.List;

public interface OrderDomainService {
    Order create(User orderOwner, OrderDTO dto);

    Order get(Long orderID);

    List<Order> findOwners(String ownerID);

    Order assign(Long orderID, User user);

    Order confirm(Long orderID, User assignedSupplier);

    Order decline(Long orderID, User decliner);

    List<Order> fetch(OrderStatus status, boolean active);

    Order ready(Long orderID);

    Order going(Long orderID);

    Order delivered(Long orderID);

    Order gotSelf(Long orderID);
}
