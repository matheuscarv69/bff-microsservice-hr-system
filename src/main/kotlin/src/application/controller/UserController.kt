package src.application.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import src.application.request.NewUserRequest
import src.application.response.DetailUserResponse
import src.domain.usecase.CreateUserUseCase
import src.domain.usecase.GetUserUseCase
import javax.validation.Valid

@Api(tags = ["User"])
@RestController
@RequestMapping("/v1/users")
class UserController(
    @Autowired
    private val createUserService: CreateUserUseCase,

    @Autowired
    private val getUserService: GetUserUseCase
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @ApiOperation("Register New User")
    @ApiResponses(
        value = [
            ApiResponse(code = 201, message = "Successfully registered user"),
            ApiResponse(code = 400, message = "Poorly Formatted Request"),
            ApiResponse(code = 500, message = "Internal Server Error"),
            ApiResponse(code = 504, message = "Gateway Timeout")
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createUser(
        @RequestBody @Valid request: NewUserRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        log.info("Receiving request for create User: ${request.name}")

        val userId = createUserService.createUser(request)

        val uri = uriBuilder.path("/v1/users/{userId}").buildAndExpand(userId).toUri()

        return ResponseEntity.created(uri).build()
    }

    @ApiOperation("Get User by ID")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "User found successfully"),
            ApiResponse(code = 404, message = "User Not Found"),
            ApiResponse(code = 500, message = "Internal Server Error"),
            ApiResponse(code = 504, message = "Gateway Timeout")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: Long
    ): ResponseEntity<DetailUserResponse> {
        log.info("Receiving request for found user, id: $userId")

        val response = getUserService.getUserById(userId)

        return ResponseEntity.ok(DetailUserResponse(response))
    }


}