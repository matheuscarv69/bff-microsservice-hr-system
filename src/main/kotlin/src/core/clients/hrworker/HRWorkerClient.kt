package src.core.clients.hrworker

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import src.core.clients.hrworker.request.NewWorkerHRWorkerRequest

@FeignClient(name = "hr-worker", url = "\${url.hr.worker}")
interface HRWorkerClient {

    @PostMapping("/v1/workers")
    fun createWorker(request: NewWorkerHRWorkerRequest): ResponseEntity<Void>

}