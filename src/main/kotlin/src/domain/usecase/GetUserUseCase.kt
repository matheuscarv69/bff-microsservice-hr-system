package src.domain.usecase

import src.domain.model.UserModel

interface GetUserUseCase {

    fun getUserById(userId: Long): UserModel

//    fun getAllUser(active: Boolean, pageable: Pageable): Page<User>

}
