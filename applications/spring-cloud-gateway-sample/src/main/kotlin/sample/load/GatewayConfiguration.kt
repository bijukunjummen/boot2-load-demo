package sample.load

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfiguration {
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String

    @Bean
    fun routes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes() {
                route(id = "passthrough", uri = targetHost) {
                    path("/passthrough/{segment}")

                    filters {
                        setPath("/{segment}")
                    }
                }
            }
}