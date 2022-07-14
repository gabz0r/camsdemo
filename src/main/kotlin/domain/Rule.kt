package domain

import Part
import javax.script.ScriptEngineManager

data class Rule(
    val ruleId: Int,
    val ruleName: String,
    val expression: String
) {
    /**
     * In SIVAS PL/SQL, hier JS
     * funktion evaluiert die aktuelle regel [this] gegen [onPart]
     * @param onPart [Part], gegen welches die regel geprueft werden soll
     * @return true, falls regel passt, false, falls eben nicht
     */
    fun evaluate(onPart: Part): Boolean {
        //JS engine erzeugen
        ScriptEngineManager().getEngineByName("javascript")?.let { jsEngine ->

            /**
             * verknuepfte [Property] (koennen auch mehrere sein) extrahieren
             * syntax immer -> :propertyId: (vgl. merkmalsnummer sivas)
             */
            val referencedProps = mutableListOf<String>()
            val referencedPropertiesIterator = Regex(":([^:]*):").findAll(expression).iterator()

            while (referencedPropertiesIterator.hasNext()) {
                referencedPropertiesIterator.next().groups[1]?.value?.let {
                    referencedProps.add(it)
                }
            }

            // regelausdruck aufbereiten
            var expressionToEvaluate = expression.replace(":", "")

            /**
             * merkmal ueber propertyId aus [onPart] lesen
             * anschliessend in zu evaluierendem ausdruck ersetzen
             */
            referencedProps.forEach { refProp ->
                val propOnPart = onPart.properties.firstOrNull { it.propertyId.toString() == refProp }
                expressionToEvaluate = expressionToEvaluate.replace(refProp, propOnPart?.value.toString())
            }

            // ausdruck mit js engine auswerten und raus
            return jsEngine.eval(expressionToEvaluate) as? Boolean ?: false
        }

        return false
    }

    companion object {
        val demoDataRuleRed: List<Rule>
        get() {
            return listOf(
                Rule(
                    ruleId = 0,
                    ruleName = "Schrank groesser 100cm dann rote Griffe",
                    /**
                     * merkmal 4711, siehe [Property.demoData] und [Property.propertyId]
                     */
                    expression = ":4711: > 100"
                )
            )
        }

        val demoDataRuleBlue: List<Rule>
            get() {
                return listOf(
                    Rule(
                        ruleId = 0,
                        ruleName = "Schrank kleiner gleich 100cm dann blaue Griffe",
                        expression = ":4711: <= 100"
                    )
                )
            }
    }
}
