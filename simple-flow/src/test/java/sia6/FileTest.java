package sia6;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author sxs
 * @date 2023/8/21 22:32
 */
@SpringBootTest
public class FileTest {


    @Autowired
    FileWriterGateway fileWriterGateway;

    @Autowired
    Environment env;

    @Test
    public void test() {

        fileWriterGateway.writeFile("test.txt", "hello");
    }

    @Test
    public void test1() {

        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            fileWriterGateway.writeFile("simple.txt", "Hello, Spring Integration! (" + profile + ")");
        } else {
            System.out.println("No active profile set. Should set active profile to one of xmlconfig, javaconfig, or javadsl.");
        }
    }


}
