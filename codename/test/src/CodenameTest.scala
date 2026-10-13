package codename

import utest.*

object CodenameTest extends TestSuite:

  def hex(s: String): Array[Byte] =
    s.grouped(2).map(Integer.parseInt(_, 16).toByte).toArray

  val spec = Seq(
    Spec.Adverb, Spec.Separator("-"),
    Spec.Adjective, Spec.Separator("-"),
    Spec.Noun
  )

  val tests = Tests:
    test("zero"):
      fromHash(Array.fill[Byte](32)(0), spec*) ==> "abundantly-absurd-abyss"
    test("mixed radix"):
      // 1 selects the second adverb, adverbs.length selects the second adjective
      val n = BigInt(1) + BigInt(adverbs.length)
      fromHash(n.toByteArray.reverse.padTo(32, 0.toByte).reverse, spec*) ==>
        "across-accomplished-abyss"
    test("pinned"):
      // sha256("hello"); if this fails, the word lists have changed and all
      // existing hash-derived codenames are affected
      val h = hex("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824")
      fromHash(h, spec*) ==> "even-open-yaw"
      fromHash(h, Spec.Greek, Spec.Separator("-"), Spec.Digit, Spec.Digit) ==> "nu-71"
    test("deterministic"):
      val h = Array.tabulate[Byte](32)(_.toByte)
      fromHash(h, spec*) ==> fromHash(h.clone(), spec*)
    test("short"):
      fromHash(Array[Byte](0, 1), spec*) ==> "across-absurd-abyss"
      fromHash(Array.empty[Byte], spec*) ==> "abundantly-absurd-abyss"
