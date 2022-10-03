package od.shelter.deliveryservice.controller;

import od.shelter.deliveryservice.dao.model.User;
import od.shelter.deliveryservice.dto.UserDTO;
import od.shelter.deliveryservice.service.UserService;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import od.shelter.deliveryservice.utils.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final EntityMapper<User, UserDTO> mapper;

    @Autowired
    public UserController(UserService service, EntityMapper<User, UserDTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO dto) {
        return mapper.dto(service.create(dto));
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable String id) {
        return mapper.dto(service.get(id));
    }

    @GetMapping("/fetch/{role}")
    public List<UserDTO> fetch(@PathVariable Role role) {
        return service.fetch(role).stream().map(mapper::dto).toList();
    }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable String id) {
        return mapper.dto(service.delete(id));
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO dto) {
        return mapper.dto(service.update(id, dto));
    }
}
