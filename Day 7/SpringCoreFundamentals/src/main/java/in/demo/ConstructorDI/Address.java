package in.demo.ConstructorDI;

import org.springframework.context.annotation.Bean;

public class Address {
    @Bean
    void getAddress() {
        System.out.println("Address of Employee bean");
    }
}
