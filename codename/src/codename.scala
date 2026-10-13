package codename

val adverbs = Array(
  "abundantly",
  "across",
  "amiss",
  "approximately",
  "awful",
  "awry",
  "basic",
  // "beforehand",
  "earnestly",
  "essentially",
  "even",
  "extra",
  "flat",
  "forthwile",
  "greatly",
  "henceforth",
  "hereinafter",
  "hitherto",
  "imminent",
  "indeed",
  "infra",
  "inter",
  "low",
  "ludicrously",
  "master",
  "nearby",
  "neither",
  "never",
  "newly",
  "nigh",
  "nonethless",
  "not",
  "otherwise",
  "overall",
  "quick",
  "real",
  "soon",
  "sparsely",
  "then",
  "thereafter",
  "throughout",
  "too",
  "upright",
  "very"
)

val adjectives = Array(
  "absurd",
  "accomplished",
  "adjacent",
  "arbitrary",
  "astral",
  "axial",
  "azure",
  "blinking",
  "braided",
  "catchy",
  "closed",
  "complex",
  "considerate",
  "credible",
  "cromulent",
  "dark",
  "essential",
  "extraneous",
  "extreme",
  "fabulous",
  "false",
  "ferocious",
  "flashy",
  "forbidden",
  "furious",
  "glacial",
  "grand",
  "grandiose",
  "great",
  "hidden",
  "high",
  "imaginary",
  "imminent",
  "inherent",
  "intrinsic",
  "invidious",
  "jovial",
  "jumpy",
  "light",
  "little",
  "low",
  "ludicrous",
  "lunar",
  "major",
  "mellow",
  "minor",
  "negative",
  "new",
  "nodal",
  "open",
  "optimistic",
  "optuse",
  "patchy",
  "penurious",
  "perplex",
  "pickled",
  "polar",
  "positive",
  "private",
  "rare",
  "random",
  "rigorous",
  "sentient",
  "serene",
  "serious",
  "sigmoid",
  "solar",
  "sparse",
  "specific",
  "stable",
  "steep",
  "top",
  "true",
  "ubiquitous",
  "unbalanced",
  "united",
  "universal",
  "vast",
  "vindictive",
  "voodoo",
  "whitty",
  "wide",
  "wimpy",
  "windy",
  "xenial",
  "xenolithic",
  // "yay",
  "zillion",
  "zoic"
)

val nouns = Array(
  "abyss",
  "aleph",
  "analyst",
  "azimuth",
  "bet",
  "bingo",
  "cat",
  "catch",
  "cigar",
  "clone",
  "clown",
  "code",
  "condor",
  "conjugate",
  "cow",
  "crow",
  "dark",
  "eagle",
  "echo",
  "edition",
  "emu",
  "flume",
  "fountain",
  "giraffe",
  "graphite",
  "gazelle",
  "hall",
  "high",
  "icicle",
  "index",
  "jack",
  "jar",
  "jiffy",
  "jest",
  "joke",
  "joker",
  "khi",
  "king",
  "kite",
  "light",
  "label",
  "lima",
  "loop",
  "low",
  "macro",
  "mage",
  "module",
  "mutton",
  "nest",
  "nil",
  "nomenclature",
  "ostrich",
  "overnumerousness",
  "parcel",
  "pin",
  "pyrite",
  "quartz",
  "queen",
  "quill",
  "radish",
  "resource",
  "rick",
  "route",
  "rules",
  "sentinel",
  "set",
  "sheep",
  "sigma",
  "six",
  "slam",
  "smoke",
  "source",
  "spinel",
  "thunder",
  "tag",
  "top",
  "topaz",
  "tulip",
  "umbrella",
  "unit",
  "user",
  "vow",
  "wolf",
  "xenium",
  "yaw",
  "yield",
  "zenith",
  "zulu"
)

val greek = Array(
  "alpha",
  "beta",
  "gamma",
  "delta",
  "epsilon",
  "zeta",
  "eta",
  "theta",
  "iota",
  "kappa",
  "lambda",
  "mu",
  "nu",
  "xi",
  "omicron",
  "pi",
  "rho",
  "sigma",
  "tau",
  "upsilon",
  "phi",
  "chi",
  "psi",
  "omega"
)

enum Spec:
  case Adverb
  case Adjective
  case Noun
  case Separator(str: String)
  case Digit
  case Greek

private val random = SecureRandom()

/** Generate a codename, using `choose` to pick an index in [0, limit) for
  * every element of the spec that requires a choice. */
private def generateWith(spec: Spec*)(choose: Int => Int): String =
  val builder = collection.mutable.StringBuilder()

  def next(words: Array[String]) =
    builder ++= words(choose(words.length))

  spec.foreach:
    case Spec.Adverb => next(adverbs)
    case Spec.Adjective => next(adjectives)
    case Spec.Noun => next(nouns)
    case Spec.Separator(str) => builder ++= str
    case Spec.Digit => builder ++= choose(10).toString
    case Spec.Greek => next(greek)

  builder.result()

/** Generate a random codename. */
def generate(spec: Spec*): String =
  generateWith(spec*)(random.nextInt)

/** Deterministically derive a codename from a hash.
  *
  * Note that the result is NOT guaranteed to be stable across different
  * versions of this library. In particular, if any word is added or removed
  * from a word list, the result will change. Hence, while it's ok to use this
  * function to compute a codename from a hash, you should not use it re-compute
  * a codename and compare them.
  */
def fromHash(hash: Array[Byte], spec: Spec*): String =
  var n = BigInt(1, hash)
  generateWith(spec*): limit =>
    val (q, r) = n /% limit
    n = q
    r.toInt

def entropy(spec: Spec*): Double =
  val partials = spec.map:
    case Spec.Adverb =>
      math.log(adverbs.length) / math.log(2)
    case Spec.Adjective =>
      math.log(adjectives.length) / math.log(2)
    case Spec.Noun =>
      math.log(nouns.length) / math.log(2)
    case Spec.Digit =>
      math.log(10) / math.log(2)
    case Spec.Greek =>
      math.log(greek.length) / math.log(2)
    case _ => 0
  partials.foldLeft(0.0)(_ + _)
