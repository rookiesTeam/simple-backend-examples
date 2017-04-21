package guestbook.model

case class GuestComment(id: Int, guest: String, comment: String)

object GuestComment {
  
  def apply(guestComment: (Int, String, String)) = guestComment match {
    case (i, g, c) => new GuestComment(i, g, c)
  }
  
}
