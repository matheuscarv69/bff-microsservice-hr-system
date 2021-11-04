package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.configs.exceptions.ExternalTimeoutException
import src.configs.exceptions.ExternalUserNotFoundException
import src.core.clients.hrworker.HRWorkerClient
import src.core.clients.hrworker.request.NewWorkerHRWorkerRequest
import src.core.clients.hrworker.response.DetailWorkerHRWorkerResponse

@Component
class HRWorkerAdapter(
    @Autowired
    private val hrWorkerClient: HRWorkerClient
) {

    companion object {
        const val ERROR_INTEGRATION: String = "Error hr-worker integration"
        const val MS_NAME: String = "HR Worker"
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createWorker(request: NewWorkerHRWorkerRequest): Long {
        log.info("Sending request for create worker in $MS_NAME")

        try {
            val response = hrWorkerClient.createWorker(request)

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

    fun getWorkerById(workerId: Long): DetailWorkerHRWorkerResponse {
        log.info("Sending request for Get Worker by ID: $workerId in $MS_NAME")

        try {
            val response = hrWorkerClient.getWorkerById(workerId)

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