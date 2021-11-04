package src.core.clients.hrworker

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import src.core.clients.hrworker.request.NewWorkerHRWorkerRequest
import src.core.clients.hrworker.response.DetailWorkerHRWorkerResponse

@FeignClient(name = "hr-worker", url = "\${url.hr.worker}")
interface HRWorkerClient {

    @PostMapping("/v1/workers")
    fun createWorker(request: NewWorkerHRWorkerRequest): ResponseEntity<Void>

    @GetMapping("/v1/workers/{workerId}")
    fun getWorkerById(@PathVariable workerId: Long): ResponseEntity<DetailWorkerHRWorkerResponse>

}