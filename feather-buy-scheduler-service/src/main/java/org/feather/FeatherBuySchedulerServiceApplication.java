package org.feather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FeatherBuySchedulerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatherBuySchedulerServiceApplication.class, args);
    }

}
