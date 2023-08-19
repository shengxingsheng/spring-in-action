package sia6;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author sxs
 * @date 2023/8/19 21:51
 */

@MessagingGateway(defaultRequestChannel = "testInChannel")
public interface FileWriterGateway {

    void writeFile(@Header(FileHeaders.FILENAME) String fileName, String data);

}
