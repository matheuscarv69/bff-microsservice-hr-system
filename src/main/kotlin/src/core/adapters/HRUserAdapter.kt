package src.core.adapters

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import src.configs.exceptions.ExternalTimeoutException
import src.configs.exceptions.ExternalUserNotFoundException
import src.core.clients.hruser.HRUserClient
import src.core.clients.hruser.exceptions.HrUserEmailAlreadyExistsException
import src.core.clients.hruser.request.NewUserHRUserRequest
import src.core.clients.hruser.response.DetailUserHRUserResponse

@Component
class HRUserAdapter(
    @Autowired
    private val hrUserClient: HRUserClient
) {

    companion object {
        const val ERROR_INTEGRATION: String = "Error hr-user integration"
        const val MS_NAME: String = "HR-User"
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createUser(request: NewUserHRUserRequest): Long {
        log.info("Sending request for create user in $MS_NAME")

        try {
            val response = hrUserClient.createUser(request)

            val userId = response.headers.location!!.path.split("/").last().toLong()

            return userId
        } catch (exception: FeignException) {
            log.warn(ERROR_INTEGRATION)

            when (exception.status()) {
                400 -> {
                    exception.message!!.substringAfterLast("\"fields\":").let { fieldError ->
                        if (fieldError.contains("\"email\":\"Email informed already exists, try again with other email.\""))
                            throw HrUserEmailAlreadyExistsException()
                    }
                }
                500 -> throw ExternalTimeoutException(MS_NAME)

            }

            throw RuntimeException(ERROR_INTEGRATION)
        }

    }

    fun getUserById(userId: Long): DetailUserHRUserResponse {
        log.info("Sending request for Get User by ID: $userId in $MS_NAME")

        try {
            val response = hrUserClient.getUserById(userId)

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