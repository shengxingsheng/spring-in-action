package tacos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author sxs
 * @date 2023/8/11 23:02
 */
@RepositoryRestResource(path = "tacos")
public interface TacoRepository extends JpaRepository<Taco, Long> {

}
