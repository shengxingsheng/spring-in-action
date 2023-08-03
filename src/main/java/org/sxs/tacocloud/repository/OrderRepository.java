package org.sxs.tacocloud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.sxs.tacocloud.domain.TacoOrder;

import java.util.Date;
import java.util.List;

/**
 * @Author sxs
 * @Date 2023/7/30 13:20
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    List<TacoOrder> findByDeliveryNameAndDeliveryCityAllIgnoreCase(String deliveryName, String deliveryCity);

    List<TacoOrder> findByDeliveryCityOrderByDeliveryName(String city);

    @Query("from TacoOrder o where o.deliveryCity='Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();
}
