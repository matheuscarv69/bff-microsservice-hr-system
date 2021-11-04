package src.domain.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import src.core.adapters.HRPayrollAdapter
import src.core.adapters.HRUserAdapter
import src.core.adapters.HRWorkerAdapter
import src.domain.model.UserModel
import src.domain.usecase.GetUserUseCase

@Service
class GetUserService(

    @Autowired
    private val hrUserAdapter: HRUserAdapter,

    @Autowired
    private val hrWorkerAdapter: HRWorkerAdapter,

    @Autowired
    private val hrPayrollAdapter: HRPayrollAdapter

) : GetUserUseCase {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun getUserById(userId: Long): UserModel {
        log.info("Getting User by ID: $userId")

        val hrUserResponse = hrUserAdapter.getUserById(userId)
        val hrWorkerResponse = hrWorkerAdapter.getWorkerById(userId)
        val hrPayrollResponse = hrPayrollAdapter.getWorkerById(userId)

        return UserModel(
            hrUserResponse,
            hrWorkerResponse,
            hrPayrollResponse
        )
    }

//    override fun getAllUser(active: Boolean, pageable: Pageable): Page<User> {
//        log.info("Getting all Users")
//
//        return repository.findAllByActive(active, pageable)
//
//    }


}