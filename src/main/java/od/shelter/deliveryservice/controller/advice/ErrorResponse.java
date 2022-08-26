package od.shelter.deliveryservice.controller.advice;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    private LocalDateTime timestamp;
    private short code;
    private String error;
    private String message;
    private String path;
}