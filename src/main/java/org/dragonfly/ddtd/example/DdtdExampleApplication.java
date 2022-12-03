package org.dragonfly.ddtd.example;

import org.dragonfly.ddtd.spring.DdtdBootstrapConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Bootstrap entrance
 * @author jian.dong1
 */
@SpringBootApplication
@Import(DdtdBootstrapConfiguration.class)
public class DdtdExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdtdExampleApplication.class, args);
    }

}
