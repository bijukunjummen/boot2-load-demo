package sample.load

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class MessageHandler {
    fun handleMessage(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(Message::class.java).flatMap { m ->
            Mono
                    .fromCallable({ MessageAck(id = m.id, received = m.payload, ack = "ack") })
                    .delayElement(Duration.ofMillis(m.delay))
                    .flatMap { messageAck ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .body(fromObject(messageAck))
                    }
        }
    }

}