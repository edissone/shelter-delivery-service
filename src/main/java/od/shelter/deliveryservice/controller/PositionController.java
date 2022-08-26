package od.shelter.deliveryservice.controller;

import od.shelter.deliveryservice.dao.model.Position;
import od.shelter.deliveryservice.dto.PositionDTO;
import od.shelter.deliveryservice.service.PositionService;
import od.shelter.deliveryservice.utils.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {
    private final EntityMapper<Position, PositionDTO> mapper;
    private final PositionService service;

    @Autowired
    public PositionController(EntityMapper<Position, PositionDTO> mapper, PositionService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<PositionDTO> fetch(@RequestParam(required = false) String category) {
        return service.fetch(category).stream().map(mapper::dto).toList();
    }
}
