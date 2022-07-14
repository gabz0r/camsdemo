import domain.Property
import domain.Rule

data class Part(
    var partId: Int,
    var partName: String,
    var parentPartId: Int?,
    var properties: List<Property> = listOf(),
    var rules: List<Rule> = listOf()
) {
    companion object {

        /**
         *   Stammstueckliste fuer Schubladenschrank xyz, auf ebene 3 dann 2 moeglichkeiten, entweder griffe rot oder griffe blau fuer den schubladeneinschub
         *   Merkmale sind an oberster ebene definiert (macht fuer den anfang denke ich am meisten sinn, feld [properties], siehe auch [Property.demoData]
         *   Regeln (oder Bedingungen?) sind dann jeweils an den Schubladengriffen hinterlegt, feld [rules], siehe auch [Rule.demoDataRuleRed] und [Rule.demoDataRuleBlue]
         *
         *   ACHTUNG: Die Stammstueckliste hat hier keine struktur ueber referenzen (Liste "children" oder sowas), abhaengigkeiten stehen ueber den losen bezug partId und parentPartId. Ansonsten ist die liste flach
         */
        val demoData: List<Part>
        get() {
            return listOf(
                Part(
                    partId = 0,
                    partName = "Schubladenschrank xyz",
                    parentPartId = null,
                    // hier merkmale zuweisen
                    properties = Property.demoData
                ),
                Part(
                    partId = 1,
                    partName = "Schubladeneinschub xyz",
                    parentPartId = 0
                ),
                Part(
                    partId = 2,
                    partName = "Schubladengriff rot",
                    parentPartId = 1,
                    // hier regel zuweisen
                    rules = Rule.demoDataRuleRed
                ),
                Part(
                    partId = 3,
                    partName = "Schubladengriff blau",
                    parentPartId = 1,
                    // auch hier ne regel
                    rules = Rule.demoDataRuleBlue
                )
            )
        }
    }
}