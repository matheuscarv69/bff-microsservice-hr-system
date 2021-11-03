package src.core.clients.hrworker.request

import src.application.request.NewUserRequest
import java.math.BigDecimal

data class NewWorkerHRWorkerRequest(
    val userId: Long,
    val dailyIncome: BigDecimal,
    val department: String
) {

    constructor(userId: Long, request: NewUserRequest) : this(
        userId = userId,
        dailyIncome = request.dailyIncome,
        department = request.department
    )

}
