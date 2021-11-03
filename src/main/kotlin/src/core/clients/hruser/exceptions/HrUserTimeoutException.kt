package src.core.clients.hruser.exceptions

class HrUserTimeoutException(
    message: String = "Hr User Did Not Respond."
) : RuntimeException(message)
