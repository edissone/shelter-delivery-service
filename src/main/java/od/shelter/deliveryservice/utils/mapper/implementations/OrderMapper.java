package od.shelter.deliveryservice.utils.mapper.implementations;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements EntityMapper<Order, OrderDTO> {
    @Override
    public OrderDTO dto(Order entity) {
        return OrderDTO.builder()
                .id(entity.getId())
                .ownerID(getTgID(entity.getOwner()))
                .deliveryID(getTgID(entity.getDelivery()))
                .supplierID(getTgID(entity.getSupplier()))
                .amount(entity.getAmount())
                .paybackFrom(entity.getPaybackFrom())
                .payback(entity.getPaybackFrom() != null ? entity.getPaybackFrom() - entity.getAmount() : null)
                .logs(entity.getLogs())
                .deliveryInfo(entity.getDeliveryInfo())
                .deliveryType(entity.getDeliveryType())
                .notes(entity.getNotes())
                .paymentCode(entity.getPaymentCode())
                .paymentType(entity.getPaymentType())
                .positions(entity.getPositions())
                .currentStatus(entity.getCurrentStatus().getStatus())
                .build();
    }

    private String getTgID(User user) {
        return user == null ? null : user.getTgID();
    }

}
