package codename

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.typedarray.Uint32Array

@js.native
@JSGlobal("crypto")
private object WebCrypto extends js.Object:
  def getRandomValues(array: Uint32Array): Uint32Array = js.native

/** A source of cryptographically secure random numbers, backed by the Web
  * Crypto API. */
class SecureRandom():

  private val buffer = Uint32Array(1)

  def nextInt(): Long =
    WebCrypto.getRandomValues(buffer)
    // elements of a Uint32Array are unsigned, and hence read as doubles in
    // the range [0, 2^32)
    buffer(0).toLong

  val MaxInt: Long = 0xffffffffL

  @annotation.tailrec
  final def nextInt(limit: Int): Int =
    val remainder = MaxInt % limit
    val n = nextInt()
    if n > MaxInt - remainder then
      nextInt(limit)
    else
      (n % limit).toInt
