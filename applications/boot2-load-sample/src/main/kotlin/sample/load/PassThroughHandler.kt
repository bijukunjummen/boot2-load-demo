package sample.load

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class PassThroughHandler(private val webClient: WebClient) {

    fun handle(serverRequest: ServerRequest): Mono<ServerResponse> {
        val messageMono = serverRequest.bodyToMono(Message::class.java)

        return messageMono.flatMap { message -> 
            webClient.post()
                    .uri("/messages")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromObject(message))
                    .exchange()
                    .flatMap { response: ClientResponse ->
                        val messageAckMono = response.bodyToMono(MessageAck::class.java)
                        messageAckMono.flatMap{messageAck -> ServerResponse.ok().body(BodyInserters.fromObject(messageAck))}
                    }
        }

    }
}