package sample.load

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfiguration {
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String

    @Bean
    fun routes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        return routeLocatorBuilder
                .routes()
                .route("passthrough", { r ->
                    r.path("/passthrough/{segment}")
                            .setPath("/{segment}")
                            .uri(targetHost)
                }).build()
    }

}