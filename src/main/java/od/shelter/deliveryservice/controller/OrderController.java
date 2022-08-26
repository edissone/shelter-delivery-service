package od.shelter.deliveryservice.controller;

import od.shelter.deliveryservice.dao.model.Order;
import od.shelter.deliveryservice.dto.OrderDTO;
import od.shelter.deliveryservice.service.OrderService;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import od.shelter.deliveryservice.utils.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final EntityMapper<Order, OrderDTO> mapper;

    @Autowired
    public OrderController(OrderService service, EntityMapper<Order, OrderDTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody OrderDTO dto) {
        return mapper.dto(service.create(dto));
    }

    @GetMapping("/{orderID}")
    public OrderDTO get(@PathVariable Long orderID) {
        return mapper.dto(service.get(orderID));
    }

    @GetMapping("/owner/{ownerID}")
    public List<OrderDTO> get(@PathVariable String ownerID) {
        return service.findOwners(ownerID).stream().map(mapper::dto).toList();
    }

    @GetMapping("/all")
    public List<OrderDTO> all(@RequestParam(required = false) OrderStatus status){
        return service.fetch(status).stream().map(mapper::dto).toList();
    }

    @PutMapping("/assign/{orderID}/{userTGID}")
    public OrderDTO assign(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.assign(orderID, userTGID));
    }

    @PutMapping("/confirm/{orderID}/{userTGID}")
    public OrderDTO confirm(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.confirm(orderID, userTGID));
    }

    @PutMapping("/ready/{orderID}/{userTGID}")
    public OrderDTO ready(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.ready(orderID, userTGID));
    }

    @PutMapping("/going/{orderID}/{userTGID}")
    public OrderDTO going(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.going(orderID, userTGID));
    }

    @PutMapping("/delivered/{orderID}/{userTGID}")
    public OrderDTO delivered(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.delivered(orderID, userTGID));
    }

    @DeleteMapping("/decline/{orderID}/{userTGID}")
    public OrderDTO decline(@PathVariable Long orderID, @PathVariable String userTGID){
        return mapper.dto(service.decline(orderID, userTGID));
    }
}
