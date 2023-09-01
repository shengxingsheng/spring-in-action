package tacos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.domain.TacoOrder;

/**
 * @author sxs
 * @date 9/1/2023 11:26
 */
public interface OrderRepository extends ReactiveCrudRepository<TacoOrder,Long> {
}
