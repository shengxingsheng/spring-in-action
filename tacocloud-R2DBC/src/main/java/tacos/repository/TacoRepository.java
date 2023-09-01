package tacos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.domain.Taco;

/**
 * @author sxs
 * @date 9/1/2023 11:29
 */
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
