package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.core.clients.hruser.HRUserClient
import src.core.clients.hruser.exceptions.HrUserEmailAlreadyExistsException
import src.core.clients.hruser.exceptions.HrUserTimeoutException
import src.core.clients.hruser.request.NewUserHRUserRequest

@Component
class HRUserAdapter(
    @Autowired
    private val hrUserClient: HRUserClient
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createUser(request: NewUserHRUserRequest): Long {
        log.info("Sending request for create user in HR-User")

        try {
            val response = hrUserClient.createUser(request)

            val userId = response.headers.location!!.path.split("/").last().toLong()

            return userId
        } catch (exception: FeignException) {
            log.warn("Error hr-user integration")

            when (exception.status()) {
                400 -> {
                    exception.message!!.substringAfterLast("\"fields\":").let { fieldError ->
                        if (fieldError.contains("\"email\":\"Email informed already exists, try again with other email.\""))
                            throw HrUserEmailAlreadyExistsException()
                    }
                }
                500 -> throw HrUserTimeoutException()

            }

            throw RuntimeException("Error hr-user integration")
        }

    }


}