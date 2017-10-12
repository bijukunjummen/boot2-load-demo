package sample.load.web

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import sample.load.Message
import sample.load.MessageHandler
import sample.load.config.RoutesConfig

@RunWith(SpringRunner::class)
@WebFluxTest(controllers = arrayOf(RoutesConfig::class, MessageHandler::class))
class MessageHandlerControllerTest {
    
    @Autowired
    private lateinit var webTestClient: WebTestClient
    
    @Test
    fun testCallToMessageEndpoint() {
        webTestClient.post().uri("/messages")
                .body(BodyInserters.fromObject(Message("1", "one", 0)))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .json(""" 
                    | {
                    |   "id": "1",
                    |   "received": "one",
                    |   "ack": "ack"
                    | }
                """.trimMargin())
    }
    
}