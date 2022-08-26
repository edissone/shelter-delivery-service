package od.shelter.deliveryservice.utils.mapper;

public interface EntityMapper <T, D>{
    D dto(T entity);
}
