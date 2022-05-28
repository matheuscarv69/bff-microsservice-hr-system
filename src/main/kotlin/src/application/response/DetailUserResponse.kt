package src.application.response

import src.domain.model.UserModel
import java.math.BigDecimal

data class DetailUserResponse(
    val name: String,

    val email: String,

    val createdAt: String,

    val dailyIncome: BigDecimal,

    val department: String
) {

    constructor(userModel: UserModel) : this(
        name = userModel.name,
        email = userModel.email,
        createdAt = userModel.createdAt,
        dailyIncome = userModel.dailyIncome,
        department = userModel.department
    )
}