package sample.load

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SampleLoadApplication {

    @Bean
    fun commonTags(): MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer { registry ->
            registry.config()
                    .commonTags("application", "sample-load-target")
        }
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(SampleLoadApplication::class.java, *args)
}