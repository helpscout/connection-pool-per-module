package helpscout.shared.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import helpscout.shared.config.ModuleDataSourceProperties.InstanceProperties
import helpscout.shared.config.ModuleName.DEFAULT
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource
import kotlin.properties.Delegates.notNull

@Configuration
@EntityScan("helpscout")
@EnableConfigurationProperties(ModuleDataSourceProperties::class)
class SpringDataJpaConfiguration {

    @Bean
    @Primary
    fun dataSource(dsProperties: ModuleDataSourceProperties): DataSource {
        val targetDataSources = dsProperties.instances.associate { instance ->
            ModuleName.getStartingWith(instance.name) to dsProperties.initDataSource(instance)
        }

        return createRoutingDataSource(targetDataSources)
    }

    private fun ModuleDataSourceProperties.initDataSource(instance: InstanceProperties) =
        (initializeDataSourceBuilder().build() as HikariDataSource).apply {
            poolName = instance.name
            maximumPoolSize = instance.poolSize
            connectionTimeout = hikari.connectionTimeout
            maxLifetime = hikari.maxLifetime
        }

    private fun createRoutingDataSource(targetDataSources: Map<ModuleName, DataSource>) =
        ModuleRoutingDataSource().apply {
            setTargetDataSources(targetDataSources.toMap())
            setDefaultTargetDataSource(targetDataSources.getValue(DEFAULT))
        }
}

@ConfigurationProperties("spring.datasource")
class ModuleDataSourceProperties : DataSourceProperties() {
    lateinit var hikari: HikariConfig
    lateinit var instances: List<InstanceProperties>

    class InstanceProperties {
        lateinit var name: String
        var poolSize: Int by notNull()
    }
}