package tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.sxs.tacocloud.domain.entity.User;

/**
 * @Author sxs
 * @Date 2023/8/4 17:53
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
}
