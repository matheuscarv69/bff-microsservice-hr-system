package src.configs.exceptions

class ExternalUserNotFoundException(
    message: String
) : RuntimeException(message + " - User Not Found")
