package tacos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import tacos.domain.Users;

import java.util.List;

/**
 * @author sxs
 * @date 8/31/2023 21:49
 */
public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

}
