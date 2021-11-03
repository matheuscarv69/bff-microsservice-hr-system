package src.core.clients.hrpayroll.request

import src.application.request.NewUserRequest
import java.math.BigDecimal

data class NewWorkerHRPayrollRequest(
    val userId: Long,
    val dailyIncome: BigDecimal,
) {

    constructor(userId: Long, request: NewUserRequest) : this(
        userId = userId,
        dailyIncome = request.dailyIncome,
    )

}
