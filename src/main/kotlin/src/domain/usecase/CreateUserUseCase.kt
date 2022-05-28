package src.domain.usecase

import src.application.request.NewUserRequest

interface CreateUserUseCase {

    fun createUser(userRequest: NewUserRequest) : Long

}