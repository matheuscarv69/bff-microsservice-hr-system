package src.core.clients.hruser.request

import src.application.request.NewUserRequest

data class NewUserHRUserRequest(
    val name: String,
    val email: String,
    val password: String
) {

    constructor(request: NewUserRequest) : this(
        name = request.name,
        email = request.email,
        password = request.password
    )

}
