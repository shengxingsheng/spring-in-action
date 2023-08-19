package sia6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

/**
 * @author sxs
 * @date 2023/8/19 23:04
 */
@Configuration
public class FileWriteIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriteChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return source -> source.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriteChannel")
    public FileWritingMessageHandler fileWrite() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("C:\\Users\\sxs\\projects\\IdeaProjects\\demo\\taco-cloud\\doc\\test"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
