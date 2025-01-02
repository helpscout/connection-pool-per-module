package helpscout.notification

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class NotificationController(
    val repository: NotificationRepository
) {

    @GetMapping("/v1/notifications")
    fun listNotification(): Iterable<NotificationEntity> = repository.findAll()

    @GetMapping("/v1/notifications/{id}")
    fun getNotification(@PathVariable("id") id: Long): Optional<NotificationEntity> = repository.findById(id)
}