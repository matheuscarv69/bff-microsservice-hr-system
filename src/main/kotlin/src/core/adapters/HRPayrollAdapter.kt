package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.configs.exceptions.ExternalTimeoutException
import src.core.clients.hrpayroll.HRPayrollClient
import src.core.clients.hrpayroll.request.NewWorkerHRPayrollRequest

@Component
class HRPayrollAdapter(
    @Autowired
    private val hrPayrollClient: HRPayrollClient
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createWorker(request: NewWorkerHRPayrollRequest): Long {
        log.info("Sending request for create worker in HR-Payroll")

        try {
            val response = hrPayrollClient.createWorker(request)

            val workerId = response.headers.location!!.path.split("/").last().toLong()

            return workerId
        } catch (exception: FeignException) {
            log.warn("Error hr-payroll integration")

            when (exception.status()) {
                500 -> throw ExternalTimeoutException("HR Payroll")
            }

            throw RuntimeException("Error hr-payroll integration")
        }

    }


}