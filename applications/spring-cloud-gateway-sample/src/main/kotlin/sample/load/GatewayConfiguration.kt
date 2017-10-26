package sample.load

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.factory.GatewayFilters.setPath
import org.springframework.cloud.gateway.handler.predicate.RoutePredicates.path
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.Routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfiguration {
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String

    @Bean
    fun routes(): RouteLocator {
        return Routes.locator()
                .route("passthrough")
                .predicate(path("/passthrough/{segment}"))
                .filter(setPath("/{segment}"))
                .uri(targetHost)
                .build()
    }

}