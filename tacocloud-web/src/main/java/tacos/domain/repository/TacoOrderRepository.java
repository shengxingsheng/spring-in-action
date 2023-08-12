package tacos.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sxs.tacocloud.domain.entity.TacoOrder;
import org.sxs.tacocloud.domain.entity.User;

import java.util.Date;
import java.util.List;

/**
 * @Author sxs
 * @Date 2023/7/30 13:20
 */
@RepositoryRestResource(path = "orders")
public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    List<TacoOrder> findByDeliveryNameAndDeliveryCityAllIgnoreCase(String deliveryName, String deliveryCity);

    List<TacoOrder> findByDeliveryCityOrderByDeliveryName(String city);

    @Query("from TacoOrder o where o.deliveryCity='Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, PageRequest page);

}
