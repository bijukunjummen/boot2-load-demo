package sample.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulSampleApplication.class, args);
    }
}
