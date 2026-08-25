package codename

/** A source of cryptographically secure random numbers, backed by
  * java.security.SecureRandom. */
class SecureRandom():

  private val underlying = java.security.SecureRandom()

  def nextInt(limit: Int): Int = underlying.nextInt(limit)
