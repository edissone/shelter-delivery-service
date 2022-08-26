package od.shelter.deliveryservice.exception;

import lombok.Getter;

@Getter
public class ActiveOrdersExistException extends RuntimeException {
    private final String userID;

    public ActiveOrdersExistException(String userID) {
        super(String.format("User with id:%s already has active orders", userID));
        this.userID = userID;
    }
}
