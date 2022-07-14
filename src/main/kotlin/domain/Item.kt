package domain

import Part
/**
 * Auftragsstuecklistenposition
 * Stellt ueber [children] die liste strukturiert dar.
 * wird ueber [PartListResolver.doIt] erzeugt
 */
data class Item(
    var itemNo: Int,
    var part: Part,
    var quantity: Double,
    var children: List<Item> = listOf(),
)