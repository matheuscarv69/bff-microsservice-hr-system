package src.core.clients.hruser

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import src.core.clients.hruser.request.NewUserHRUserRequest
import src.core.clients.hruser.response.DetailUserHRUserResponse

@FeignClient(name = "hr-user", url = "\${url.hr.user}")
interface HRUserClient {

    @PostMapping("/v1/users")
    fun createUser(request: NewUserHRUserRequest): ResponseEntity<Void>

    @GetMapping("/v1/users/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<DetailUserHRUserResponse>

}