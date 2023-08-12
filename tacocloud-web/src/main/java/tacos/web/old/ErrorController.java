package tacos.web.old;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author sxs
 * @Date 2023/8/6 20:12
 */
public class ErrorController {
    public String error(AuthenticationException authException) {
        System.out.println(authException.getMessage());
        return "error";
    }
}
