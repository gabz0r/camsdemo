import business.PartListResolver
import javax.script.ScriptEngineManager

fun main(args: Array<String>) {
    println("cams part list resolving demo")
    println("=============================")

    val schubladenSchrank = Part.demoData

    val result = PartListResolver.doIt(schubladenSchrank)

    println(result)
}