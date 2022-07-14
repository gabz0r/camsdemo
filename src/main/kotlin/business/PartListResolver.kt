package business

import Part
import domain.Item
import java.lang.Exception

object PartListResolver {

    var itemLineCounter = 0
    var initialPartNode: Part? = null

    /**
     * Loest stammstueckliste auf
     * @param partListSuperSet unstrukturierte Stammstueckliste
     * @return strukturierte "Auftragsstueckliste", um mal bei SIVAS zu bleiben :D
     */
    fun doIt(partListSuperSet: List<Part>): Item {
        // einstiegspunkt abgreifen
        val parentNode = partListSuperSet.firstOrNull { it.parentPartId == null }

        parentNode?.let {
            // einstiegsknoten in object global setzen, damit nachher in rekursion auf die merkmale zugegriffen werden kann
            initialPartNode = it

            // Einstiegspunkt "Auftragsstueckliste" bauen
            val initialItemLine = Item(itemLineCounter, parentNode, 1.0)

            // uuuuuund abtauchen
            initialItemLine.children = buildSubTree(initialItemLine, partListSuperSet)
            return initialItemLine
        }

        throw Exception("No parent node defined in superset!")
    }

    /**
     * rekursiv stueckliste aufbauen
     * @param parentNode aktuelle node, deren unterstruktur aufgebaut werden soll
     * @param partListSuperSet flache auftragsstueckliste, aus der sich die aufloesung bedient
     * @return auftragsstueckliste auf der aktuellen ebene
     */
    private fun buildSubTree(parentNode: Item, partListSuperSet: List<Part>): List<Item> {
        // unterobjekte aus der sstkl holen
        val children = partListSuperSet.filter { it.parentPartId == parentNode.part.partId }

        val itemLines = mutableListOf<Item>()

        // hier gehts nur rein, wenn kinder gefunden wurden (bei unterster ebene nicht der fall, dann gehts direkt unten ans return und die liste bleibt leer)
        children.forEach { childPart ->
            // regeln fuer jedes moegliche kind ueberpruefen
            var canAddWithRulesApplied = true

            childPart.rules.forEach { rule ->
                val ruleResult = rule.evaluate(onPart = initialPartNode!!)
                canAddWithRulesApplied = canAddWithRulesApplied and ruleResult
            }

            if(canAddWithRulesApplied) {
                // regel passt? dann auftragsstuecklistenobjekt erzeugen und den naechsttieferen tree bauen
                val itemLine = Item(++itemLineCounter, childPart, 1.0)
                itemLine.children = buildSubTree(itemLine, partListSuperSet)

                itemLines.add(itemLine)
            }
        }

        return itemLines
    }

}