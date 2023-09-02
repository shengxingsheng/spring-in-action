package org.sxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sxs
 * @date 9/2/2023 2:36
 */
@SpringBootApplication
public class RsocketServerApp {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RsocketServerApp.class, args);
    }
}
