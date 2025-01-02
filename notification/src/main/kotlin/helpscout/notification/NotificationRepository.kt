package helpscout.notification

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : CrudRepository<NotificationEntity, Long>