package org.sxs.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.sxs.tacocloud.domain.TacoOrder;

/**
 * @Author sxs
 * @Date 2023/7/30 13:20
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
   
}
