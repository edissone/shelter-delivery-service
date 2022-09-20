package od.shelter.deliveryservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import od.shelter.deliveryservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    private static final String LOG_FORMAT = "{} ->\t{}";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.NOT_FOUND, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ActiveOrdersExistException.class)
    public ErrorResponse handleActiveOrdersExists(ActiveOrdersExistException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.ACTIVE_ORDERS_EXISTS, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidStateException.class)
    public ErrorResponse handleInvalidState(InvalidStateException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.INVALID_STATE, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidComputationException.class)
    public ErrorResponse handleInvalidComputation(InvalidComputationException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.INVALID_COMPUTATION, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PermissionDeniedException.class)
    public ErrorResponse handlePermissionDenied(PermissionDeniedException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.PERMISSION_DENIED, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UnassignedOrderProcessingException.class)
    public ErrorResponse handleUnassignedOrder(UnassignedOrderProcessingException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.UNASSIGNED_ORDER, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public ErrorResponse handleAlreadyExists(AlreadyExistsException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.ALREADY_EXISTS, exception, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UnsupportedOperationException.class)
    public ErrorResponse handleUnsupportedOperationException(UnsupportedOperationException exception, HttpServletRequest req) {
        log.error(LOG_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return errorResponse(ExceptionCode.PERMISSION_DENIED, exception, req.getRequestURI());
    }

    private ErrorResponse errorResponse(ExceptionCode excCode, RuntimeException exception, String uri) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .code(excCode.getCode())
                .error(exception.getClass().getSimpleName())
                .path(uri)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
