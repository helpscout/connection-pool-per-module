package helpscout.shared.config

import helpscout.shared.config.DataSourceContext.current
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class ModuleRoutingDataSource : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): ModuleName = current.also {
        println("Routing datasource to $it")
    }
}