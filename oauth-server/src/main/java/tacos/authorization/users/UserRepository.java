package tacos.authorization.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sxs
 * @date 2023/8/13 21:27
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
