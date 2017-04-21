package guestbook.database

import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._

// http://slick.lightbend.com/doc/3.2.0/
// http://slick.lightbend.com/doc/3.2.0/queries.html
object GuestCommentDAO {
  
  val db: Database = Database.forURL("jdbc:sqlite:db.sqlite", driver = "org.sqlite.JDBC") // Database connection
  val guestComments = TableQuery[GuestCommentTable]  // ORM

  // Database execution functions
  private def writeQuery[A, B <: NoStream, C <: Effect](query: SQLiteProfile.ProfileAction[A, B, C]) =
    db.run(DBIO.seq(query))

  private def readQuery[A, B](query: Query[A, B, Seq]) = db.run(query.result)

  // private def readQueryHead[A, B](query: Query[A, B, Seq]) = db.run(query.result.headOption)

  private def countQuery[A, B](query: Query[A, B, Seq]) = db.run(query.length.result)
  
  // Result query mapping (unused, but interesting option)
  // private def mapAll(result: GuestCommentTable) = (result.id, result.guest, result.comment)

  // DAO functions
  def create = writeQuery(guestComments.schema.create)
  
  def drop = writeQuery(guestComments.schema.drop)

  def insert(g: String, c: String) = writeQuery(guestComments += (-1, g, c))

  def count = countQuery(guestComments)
  
  def get(id: Int = -1, offset: Int = -1, limit: Int = -1) = (id, offset, limit) match {

    case (i, _, _) if i > -1 => readQuery(guestComments.filter(_.id === i))
    case (_, o, l) if o > -1 => {
      val r = guestComments.filter(_.id > o)
      if (l == -1) readQuery(r) else readQuery(r.take(l))
    }
    case (_, _, l) if l > -1 => readQuery(guestComments.take(l))
    case _ => readQuery(guestComments)
    
  }
  
}

class GuestCommentTable(tag: Tag) extends Table[(Int, String, String)](tag, "GuestCommentTable") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def guest = column[String]("guest")
  def comment = column[String]("comment")
  def * = (id, guest, comment)

}
