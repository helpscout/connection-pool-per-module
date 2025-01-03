package helpscout.shared.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import helpscout.shared.config.ModuleDataSourceProperties.InstanceProperties
import helpscout.shared.config.ModuleName.DEFAULT
import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource
import kotlin.properties.Delegates.notNull

@Configuration
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

    @Bean
    fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory =
        LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            this.jpaVendorAdapter = HibernateJpaVendorAdapter()
            this.setPackagesToScan("helpscout")
            this.afterPropertiesSet()
        }.`object`!!

    private fun createRoutingDataSource(targetDataSources: Map<ModuleName, DataSource>) =
        ModuleRoutingDataSource().apply {
            setTargetDataSources(targetDataSources.toMap())
            setDefaultTargetDataSource(targetDataSources[DEFAULT]!!)
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