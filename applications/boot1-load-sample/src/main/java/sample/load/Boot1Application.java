package sample.load;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Boot1Application {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTags() {
        return (registry) -> registry.config()
                .commonTags("application", "boot1-load-sample");
    }

    public static void main(String[] args) {
        SpringApplication.run(Boot1Application.class, args);
    }

}
