package helpscout.shared.config

import helpscout.shared.config.DataSourceContext.reset
import helpscout.shared.config.DataSourceContext.setDataSource
import helpscout.shared.config.ModuleName.DEFAULT
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class RoutingDataSourceTransactionInterceptors {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private fun inController() {
    }

    @Around("inController()")
    fun proceedFromController(pjp: ProceedingJoinPoint): Any? {
        val module = inferModuleName(pjp)
        return useReplica(pjp) { setDataSource(module) }
    }

    private fun inferModuleName(pjp: ProceedingJoinPoint): ModuleName {
        val packageName = pjp.staticPart.signature.declaringType.packageName
        val predicate: (ModuleName) -> Boolean = {
            packageName.contains(it.name, ignoreCase = true)
        }

        return ModuleName.entries.find(predicate) ?: DEFAULT
    }

    private fun useReplica(pjp: ProceedingJoinPoint, setDbReplica: () -> Unit): Any? = try {
        setDbReplica()
        pjp.proceed()
    } finally {
        reset()
    }
}