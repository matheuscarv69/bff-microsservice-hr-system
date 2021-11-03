package src.application.request

import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import javax.validation.constraints.*

class NewUserRequest(

    @ApiModelProperty(value = "Name", position = 1, required = true)
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,

    @ApiModelProperty(value = "Email", position = 2, required = true)
    @field:NotBlank
    @field:Size(max = 100)
    @field:Email
    val email: String,

    @ApiModelProperty(value = "Password", position = 3, required = true)
    @field:NotBlank
    @field:Size(max = 100)
    val password: String,

    @ApiModelProperty(value = "Department", position = 4, required = true)
    @field:NotBlank
    @field:Size(max = 100)
    val department: String,

    @ApiModelProperty(value = "Daily Income", position = 5, required = true)
    @field:NotNull
    @field:Positive
    val dailyIncome: BigDecimal
)
