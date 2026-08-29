import scala.scalajs.js.annotation.JSExportTopLevel

/** Entry points called from index.html. */
object Site:

  private def parse(spec: String): Seq[codename.Spec] = spec.map:
    case 'A'   => codename.Spec.Adverb
    case 'a'   => codename.Spec.Adjective
    case 'n'   => codename.Spec.Noun
    case 'd'   => codename.Spec.Digit
    case 'g'   => codename.Spec.Greek
    case other => codename.Spec.Separator(other.toString)

  @JSExportTopLevel("generate")
  def generate(spec: String): String = codename.generate(parse(spec)*)

  @JSExportTopLevel("entropy")
  def entropy(spec: String): Double = codename.entropy(parse(spec)*)
