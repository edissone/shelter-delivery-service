package od.shelter.deliveryservice.controller;

import od.shelter.deliveryservice.dao.model.ResourceParam;
import od.shelter.deliveryservice.dao.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceRepository repository;

    @Autowired
    public ResourceController(ResourceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Map<String, String> fetch() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(ResourceParam::getKey, ResourceParam::getValue));
    }
}
