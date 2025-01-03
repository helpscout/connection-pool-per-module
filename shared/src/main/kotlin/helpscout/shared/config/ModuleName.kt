package helpscout.shared.config

enum class ModuleName {
    DEFAULT,
    NOTIFICATION,
    USER;

    companion object {
        fun getStartingWith(starsWith: String): ModuleName =
            ModuleName.entries.find { starsWith.startsWith(it.name, ignoreCase = true) }!!
    }
}