package helpscout.notification

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity
@Table(name = "notification")
class NotificationEntity(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column
    val message: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = now(),
)