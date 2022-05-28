package src.core.clients.hruser.exceptions

class HrUserEmailAlreadyExistsException(
    message: String = "Email informed already exists, try again with other email."
) : RuntimeException(message)