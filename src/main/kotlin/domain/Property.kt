package domain

/**
 * Merkmal quasi
 * @param propertyId id, ueber die in regeln etc. aufs merkmal zugegriffen werden kann
 * @param name sprechender name
 * @param value irgendein wert
 */
data class Property(
    val propertyId: Int,
    val name: String,
    val value: Any
) {
    companion object {
        val demoData: List<Property>
        get() {
            return listOf(
                Property(
                    propertyId = 4711,
                    name = "Hoehe",
                    // einheit cm
                    value = 120
                ),
                Property(
                    propertyId = 1337,
                    name = "Wandstaerke",
                    // einheit cm
                    value = 1
                )
            )
        }
    }
}