package sample.zuul;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulSampleApplication.class, args);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTags() {
        return registry ->
                registry.config()
                        .commonTags("application", "zuul-sample");
    }
}
