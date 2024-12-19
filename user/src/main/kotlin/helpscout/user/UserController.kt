package helpscout.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UserController(val repository: UserRepository) {

    @GetMapping("/v1/users")
    fun listUsers(): Iterable<UserEntity> = repository.findAll()

    @GetMapping("/v1/users/{id}")
    fun getUser(@PathVariable("id") id: Long): Optional<UserEntity> = repository.findById(id)
}