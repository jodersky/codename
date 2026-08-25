package codename

/** A source of cryptographically secure random numbers, backed by
  * /dev/urandom. */
class SecureRandom():

  def nextInt(): Long =
    val ch = java.nio.channels.FileChannel.open(
      java.nio.file.Paths.get("/dev/urandom"),
      java.nio.file.StandardOpenOption.READ
    )
    val bytes = java.nio.ByteBuffer.allocate(4)
    try
      if ch.read(bytes) != 4 then sys.error("didn't read as many bytes as expected")
    finally
      ch.close()
    var r = 0L
    r |= (bytes.get(0) & 0xffL) << 24
    r |= (bytes.get(1) & 0xffL) << 16
    r |= (bytes.get(2) & 0xffL) << 8
    r |= (bytes.get(3) & 0xffL)
    r

  val MaxInt: Long = 0xffffffffL

  @annotation.tailrec
  final def nextInt(limit: Int): Int =
    val remainder = MaxInt % limit
    val n = nextInt()
    if n > MaxInt - remainder then
      nextInt(limit)
    else
      (n % limit).toInt
