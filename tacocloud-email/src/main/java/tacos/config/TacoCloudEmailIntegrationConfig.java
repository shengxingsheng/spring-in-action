package tacos.config;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;

/**
 * @author sxs
 * @date 2023/8/22 15:10
 */
@Configuration
@Slf4j
public class TacoCloudEmailIntegrationConfig {

    //@Bean
    public IntegrationFlow integrationFlow(EmailProperties emailProperties,
                                           EmailToOrderTransformer emailToOrderTransformer,
                                           OrderSubmitMessageHandler orderSubmitHandler) {
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate())))

                .transform(emailToOrderTransformer)
                .handle(orderSubmitHandler)
                .get();
    }

    @Bean
    public IntegrationFlow integrationFlow1(EmailProperties emailProperties,
                                            EmailToOrderTransformer emailToOrderTransformer,
                                            OrderSubmitMessageHandler orderSubmitHandler) {
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.autoStartup(true)
                                .poller(Pollers.fixedDelay(emailProperties.getPollRate())))
                .handle(new GenericHandler<Object>() {
                    @Override
                    public Object handle(Object payload, MessageHeaders headers) {
                        if (!(payload instanceof Message mailMessage)) {
                            log.error("消息负载不是mail");
                            return null;
                        }
                        try {
                            String subject = mailMessage.getSubject();
                            String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                            String content = mailMessage.getContent().toString();
                            log.info("subject: " + subject);
                            log.info("email: " + email);
                            log.info("content: " + content);
                        } catch (MessagingException e) {
                            log.error("MessagingException: {}", e);
                        } catch (IOException e) {
                            log.error("IOException: {}", e);
                        }
                        return null;
                    }
                })
                .get();
    }
}
