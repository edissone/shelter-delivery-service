package od.shelter.deliveryservice.exception;

import lombok.Getter;
import od.shelter.deliveryservice.utils.model.Role;

@Getter
public class PermissionDeniedException extends RuntimeException {
    private final String userID;
    private final Role role;
    private final Long orderID;

    public PermissionDeniedException(String userID, Role role, Long orderID) {
        super(
                String.format("Permissions for user with ID:%s role:%s on order with id: %d",
                        userID, role, orderID)
        );
        this.userID = userID;
        this.role = role;
        this.orderID = orderID;
    }
}
