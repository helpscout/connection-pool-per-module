package helpscout.shared.config

import helpscout.shared.config.ModuleName.DEFAULT

object DataSourceContext {

    private val contextHolder = ThreadLocal.withInitial { DEFAULT }

    @JvmStatic
    fun setDataSource(module: ModuleName) = contextHolder.set(module)

    @JvmStatic
    val current: ModuleName
        get() = contextHolder.get()

    @JvmStatic
    fun reset() = contextHolder.remove()
}