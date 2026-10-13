val help =
  """|Usage: codename [OPTIONS...] [SPECIFICATION] [NUM]
     |Generate a random codename according to a specification, a number of times
     |("A a n" 10 times by default).
     |
     |Options:
     |
     |  -h, --help        show help message
     |  -v, --version     show version information
     |  --entropy         show entropy information instead of generating code
     |                    names
     |  --hash=HEX        derive a single codename from the given hash (in
     |                    hexadecimal), instead of generating random ones
     |
     |Specification:
     |
     |  SPEC ::= { 'A' | 'a' | 'n' | 'd' | 'g' | SEP }
     |  SEP  ::= char
     |
     |where the quoted letters will be substituted randomly by the following:
     |
     |  A: an adverb
     |  a: an adjective
     |  n: a noun
     |  d: a digit [0-9]
     |  g: a greek letter (name)
     |
     |For example, the specification "A-a-n" will produce a code name such as:
     |"extra-pickled-umbrella".""".stripMargin

def main(args: Array[String]): Unit =
  val (options, arguments) = args.partition(_.startsWith("-"))

  var action = "generate"
  var hash: Array[Byte] = null

  options.foreach:
    case "-h" | "--help" =>
      println(help)
      sys.exit(0)
    case "-v" | "--version" =>
      println(BuildInfo.version)
      sys.exit(0)
    case "--entropy" =>
      action = "entropy"
    case x if x.startsWith("--hash=") =>
      val hex = x.stripPrefix("--hash=")
      if hex.isEmpty || !hex.forall(c => Character.digit(c, 16) >= 0) then
        System.err.println(s"Invalid hash '${hex}': not a hexadecimal string.")
        sys.exit(1)
      hash = (if hex.length % 2 == 0 then hex else "0" + hex)
        .grouped(2)
        .map(Integer.parseInt(_, 16).toByte)
        .toArray
      action = "hash"
    case x =>
      System.err.println(s"Invalid option '${x}'.")
      sys.exit(1)

  val (spec0: String, num: Int) = arguments match
    case Array() => ("A a n", 10)
    case Array(spec) => (spec, 10)
    case Array(spec, num) if hash != null =>
      System.err.println("A number of repetitions cannot be used with --hash.")
      sys.exit(1)
    case Array(spec, num) =>
      val n = num.toIntOption match
        case Some(n) =>
          n
        case None =>
          System.err.println(s"Invalid number of repetitions: '${num}' is not an integer")
          sys.exit(1)
      (spec, n)

  val spec1 = spec0.map:
    case 'A'   => codename.Spec.Adverb
    case 'a'   => codename.Spec.Adjective
    case 'n'   => codename.Spec.Noun
    case 'd'   => codename.Spec.Digit
    case 'g'   => codename.Spec.Greek
    case other => codename.Spec.Separator(other.toString)

  if action == "generate" then
    for _ <- 0 until num do
      println(codename.generate(spec1*))
  else if action == "hash" then
    println(codename.fromHash(hash, spec1*))
  else if action == "entropy" then
    println(f"${codename.entropy(spec1*)}%.2f")
  else
    sys.error("unknown action " + action)
