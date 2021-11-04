package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.configs.exceptions.ExternalTimeoutException
import src.configs.exceptions.ExternalUserNotFoundException
import src.core.clients.hrpayroll.HRPayrollClient
import src.core.clients.hrpayroll.request.NewWorkerHRPayrollRequest
import src.core.clients.hrpayroll.response.DetailWorkerHRPayrollResponse

@Component
class HRPayrollAdapter(
    @Autowired
    private val hrPayrollClient: HRPayrollClient
) {

    companion object {
        const val ERROR_INTEGRATION: String = "Error hr-payroll integration"
        const val MS_NAME: String = "HR Payroll"
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createWorker(request: NewWorkerHRPayrollRequest): Long {
        log.info("Sending request for create worker in $MS_NAME")

        try {
            val response = hrPayrollClient.createWorker(request)

            val workerId = response.headers.location!!.path.split("/").last().toLong()

            return workerId
        } catch (exception: FeignException) {
            log.warn(ERROR_INTEGRATION)

            when (exception.status()) {
                500 -> throw ExternalTimeoutException(MS_NAME)
            }

            throw RuntimeException(ERROR_INTEGRATION)
        }

    }

    fun getWorkerById(workerId: Long): DetailWorkerHRPayrollResponse {
        log.info("Sending request for Get Worker by ID: $workerId in $MS_NAME")

        try {
            val response = hrPayrollClient.getWorkerById(workerId)

            return response.body!!
        } catch (exception: FeignException) {
            log.warn(ERROR_INTEGRATION)

            when (exception.status()) {
                404 -> throw ExternalUserNotFoundException(MS_NAME)
                500 -> throw ExternalTimeoutException(MS_NAME)
            }

            throw RuntimeException(ERROR_INTEGRATION)
        }

    }


}