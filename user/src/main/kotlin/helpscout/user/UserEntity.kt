package helpscout.user

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column
    val name: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)