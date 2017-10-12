package sample.load

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SampleLoadApplication

fun main(args: Array<String>) {
    SpringApplication.run(SampleLoadApplication::class.java, *args)
}