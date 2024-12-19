package helpscout.ecommerce

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ReadingConversationController(
    val repository: PurchaseRepository
) {

    @GetMapping("/v1/purchases")
    fun listPurchases(): Iterable<PurchaseEntity> = repository.findAll()

    @GetMapping("/v1/purchases/{id}")
    fun getPurchase(@PathVariable("id") id: Long): Optional<PurchaseEntity> = repository.findById(id)
}