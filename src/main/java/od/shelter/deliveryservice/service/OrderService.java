package od.shelter.deliveryservice.service;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.utils.model.OrderStatus;

import java.util.List;

public interface OrderService {
    Order create(OrderDTO dto);

    Order get(Long orderID);

    List<Order> findOwners(String ownerID);

    Order assign(Long orderID, String userID);

    Order confirm(Long orderID, String userID);

    Order ready(Long orderID, String userID);

    Order going(Long orderID, String userTGID);

    Order delivered(Long orderID, String userTGID);

    Order gotSelf(Long orderID, String userTGID);

    Order decline(Long orderID, String userTGID);

    List<Order> fetch(OrderStatus status, boolean active);

    Order fetchAssigned(String tgID, boolean active);
}
