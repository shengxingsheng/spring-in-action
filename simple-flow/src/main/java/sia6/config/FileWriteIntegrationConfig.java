package sia6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

/**
 * @author sxs
 * @date 2023/8/19 23:04
 */
@Configuration
public class FileWriteIntegrationConfig {

    @Bean
    public IntegrationFlow fileWriteFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(t -> t.toUpperCase())
                .handle(Files.outboundAdapter(new File("C:\\Users\\sxs\\projects\\IdeaProjects\\demo\\taco-cloud\\doc\\test"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
