package tacos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tacos.domain.EmailOrder;

/**
 * @author sxs
 * @date 2023/8/22 15:16
 */
@Component
@Slf4j
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {
    private RestTemplate rest;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(ApiProperties apiProps, RestTemplate rest) {
        this.apiProps = apiProps;
        this.rest = rest;
    }

    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
//        rest.postForObject(apiProps.getUrl(), payload, String.class);
        log.info("处理中");
        return null;
    }
}
