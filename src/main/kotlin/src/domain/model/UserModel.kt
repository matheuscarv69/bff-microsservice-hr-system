package src.domain.model

import src.core.clients.hrpayroll.response.DetailWorkerHRPayrollResponse
import src.core.clients.hruser.response.DetailUserHRUserResponse
import src.core.clients.hrworker.response.DetailWorkerHRWorkerResponse
import java.math.BigDecimal

class UserModel(
    val name: String,

    val email: String,

    val createdAt: String,

    val dailyIncome: BigDecimal,

    val department: String
) {

    constructor(
        hrUserResponse: DetailUserHRUserResponse,
        hrWorkerResponse: DetailWorkerHRWorkerResponse,
        hrPayrollResponse: DetailWorkerHRPayrollResponse
    ) : this(
        name = hrUserResponse.name,
        email = hrUserResponse.email,
        createdAt = hrUserResponse.createdAt,
        dailyIncome = hrPayrollResponse.dailyIncome,
        department = hrWorkerResponse.department
    )


}