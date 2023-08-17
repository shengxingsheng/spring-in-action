package tacos.authorization.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author sxs
 * @date 2023/8/16 23:56
 */
@Component
@Slf4j
public class MyFilter implements Filter, Ordered {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.error("Request URL: " + req.getRequestURI());
        Map<String, String[]> map = req.getParameterMap();
        for (String key : map.keySet()) {
            log.error("Request Params: " + key + "->" + map.get(key).toString());
        }

        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
