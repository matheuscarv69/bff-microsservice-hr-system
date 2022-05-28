package src.core.clients.hrpayroll

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import src.core.clients.hrpayroll.request.NewWorkerHRPayrollRequest
import src.core.clients.hrpayroll.response.DetailWorkerHRPayrollResponse

@FeignClient(name = "hr-payroll", url = "\${url.hr.payroll}")
interface HRPayrollClient {

    @PostMapping("/v1/workers")
    fun createWorker(request: NewWorkerHRPayrollRequest): ResponseEntity<Void>

    @GetMapping("/v1/workers/{workerId}")
    fun getWorkerById(@PathVariable workerId: Long): ResponseEntity<DetailWorkerHRPayrollResponse>


}