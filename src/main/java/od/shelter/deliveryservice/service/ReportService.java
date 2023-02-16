package od.shelter.deliveryservice.service;

import od.shelter.deliveryservice.dto.ReportDTO;
import od.shelter.deliveryservice.utils.model.ReportOptions;

import java.util.List;

public interface ReportService {
    List<ReportDTO> getReport(ReportOptions options);
}
