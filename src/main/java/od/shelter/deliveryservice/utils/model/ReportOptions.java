package od.shelter.deliveryservice.utils.model;

import java.time.LocalDate;
import java.util.function.Predicate;

public enum ReportOptions {
    TODAY(ld -> ld.equals(LocalDate.now())),
    THIS_MONTH(ld -> ld.getYear() == LocalDate.now().getYear() &&
            ld.getMonthValue() == LocalDate.now().getMonthValue()),
    ALL_TIME(ld -> true);

    private final Predicate<LocalDate> dateFilter;

    ReportOptions(Predicate<LocalDate> dateFilter) {
        this.dateFilter = dateFilter;
    }

    public Predicate<LocalDate> getDateFilter() {
        return dateFilter;
    }
}
