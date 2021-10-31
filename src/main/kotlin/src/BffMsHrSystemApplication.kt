package src

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class BffMsHrSystemApplication

fun main(args: Array<String>) {
    runApplication<BffMsHrSystemApplication>(*args)
}
