package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.configs.exceptions.ExternalTimeoutException
import src.core.clients.hrworker.HRWorkerClient
import src.core.clients.hrworker.request.NewWorkerHRWorkerRequest

@Component
class HRWorkerAdapter(
    @Autowired
    private val hrWorkerClient: HRWorkerClient
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createWorker(request: NewWorkerHRWorkerRequest): Long {
        log.info("Sending request for create worker in HR-Worker")

        try {
            val response = hrWorkerClient.createWorker(request)

            val workerId = response.headers.location!!.path.split("/").last().toLong()

            return workerId
        } catch (exception: FeignException) {
            log.warn("Error hr-worker integration")

            when (exception.status()) {
                500 -> throw ExternalTimeoutException("HR Worker")
            }

            throw RuntimeException("Error hr-worker integration")
        }

    }


}