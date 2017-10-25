package sample.load

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.factory.RewritePathWebFilterFactory
import org.springframework.cloud.gateway.filter.factory.RewritePathWebFilterFactory.*
import org.springframework.cloud.gateway.handler.predicate.RoutePredicates.path
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.Routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.tuple.TupleBuilder
import org.springframework.web.server.WebFilter

@Configuration
class GatewayConfiguration {
    @Value("\${loadtarget.host}")
    lateinit var targetHost: String;

    @Bean
    fun routes(): RouteLocator {
        return Routes.locator()
                .route("passthrough")
                .uri(targetHost)
                .predicate(path("/passthrough/messages"))
                .add(rewritePathFilter())
                .and()
                .build()
    }

    @Bean
    fun rewritePathFilter(): WebFilter {
        return RewritePathWebFilterFactory()
                .apply(TupleBuilder
                        .tuple().of(REGEXP_KEY, "/passthrough/messages", REPLACEMENT_KEY, "/messages"))
    }
}