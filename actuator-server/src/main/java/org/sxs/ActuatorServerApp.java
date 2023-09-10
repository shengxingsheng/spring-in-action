package org.sxs;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sxs
 * @date 9/9/2023 19:17
 */
@SpringBootApplication
@EnableAdminServer
public class ActuatorServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ActuatorServerApp.class, args);
    }
}