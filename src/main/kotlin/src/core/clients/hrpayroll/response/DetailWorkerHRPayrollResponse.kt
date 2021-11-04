package src.core.clients.hrpayroll.response

import java.math.BigDecimal

data class DetailWorkerHRPayrollResponse(
    val id: Long,
    val dailyIncome: BigDecimal,
    val active: Boolean
)