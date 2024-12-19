package helpscout.ecommerce

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity
@Table(name = "purchase")
class PurchaseEntity(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column
    val product: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = now(),
)