package helpscout.shared.config

enum class ModuleName {
    DEFAULT,
    ECOMMERCE,
    USER;

    companion object {
        fun getStartingWith(starsWith: String): ModuleName =
            ModuleName.entries.find { it.name.startsWith(starsWith, ignoreCase = true) }!!
    }
}