package sample.load

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class Application {
    
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String;

    @Bean
    fun webClient() : WebClient {
        return WebClient.builder()
                .baseUrl(targetHost)
                .build()
    }
    
    @Bean
    fun passThroughHandler(): PassThroughHandler {
        return PassThroughHandler(webClient())
    }

    @Bean
    fun commonTags(): MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer { registry ->
            registry.config()
                    .commonTags("application", "boot2-load-sample")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}