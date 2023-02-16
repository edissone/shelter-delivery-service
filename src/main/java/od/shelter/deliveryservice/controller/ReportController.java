package od.shelter.deliveryservice.controller;

import od.shelter.deliveryservice.dto.ReportDTO;
import od.shelter.deliveryservice.service.ReportService;
import od.shelter.deliveryservice.utils.model.ReportOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReportDTO> getReport(@RequestParam(defaultValue = "ALL_TIME", required = false) ReportOptions option) {
        return service.getReport(option);
    }
}
