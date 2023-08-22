package tacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sxs
 * @date 2023/8/22 14:48
 */

@Data
@ConfigurationProperties(prefix = "tacocloud.email")
@Component
public class EmailProperties {

    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000;
    private String port;

    public String getImapUrl() {
        String format = String.format("imaps://%s:%s@%s:%s/%s",
                this.username, this.password, this.host, this.port, this.mailbox);
        System.out.println(format);
        return format;
    }
}
