package src.configs.exceptions

class ExternalTimeoutException(
    message: String
) : RuntimeException(message + "Did Not Respond.")
