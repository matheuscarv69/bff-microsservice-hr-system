package src.domain.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.application.request.NewUserRequest
import src.core.adapters.HRUserAdapter
import src.core.clients.hruser.request.NewUserHRUserRequest
import src.domain.usecase.CreateUserUseCase

@Service
class CreateUserService(

    @Autowired
    private val hrUserAdapter: HRUserAdapter

) : CreateUserUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun createUser(userRequest: NewUserRequest) : Long{
        log.info("Sending request for external apis for register user: ${userRequest.name}")

        val hrUserIdResponse = hrUserAdapter.createUser(NewUserHRUserRequest(userRequest))

        return hrUserIdResponse
    }


}