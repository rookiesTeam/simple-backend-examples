package guestbook

import slick.jdbc.SQLiteProfile.api._

// http://slick.lightbend.com/doc/3.2.0/
// http://slick.lightbend.com/doc/3.2.0/queries.html
object GuestCommentDAO {
  
  val db: Database = Database.forURL("jdbc:sqlite:database/db.sqlite", driver = "org.sqlite.JDBC") // Database connection
  val guestComments = TableQuery[GuestComment]  // ORM

  def create = db.run(DBIO.seq(guestComments.schema.create))

  def totalGuestComments = db.run(guestComments.length.result)

  def insert(g: String, c: String) = db.run(DBIO.seq(guestComments += (-1, g, c)))
  
  def get = db.run(guestComments.result)
  
  def get(id: Int) = db.run(guestComments.filter(_.id === id).map(gc => (gc.id, gc.guest, gc.comment)).result.head)
  
  def getFrom(from: Int) = db.run(guestComments.filter(_.id > from).map(gc => (gc.id, gc.guest, gc.comment)).result)
  
}

class GuestComment(tag: Tag) extends Table[(Int, String, String)](tag, "GuestComment") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def guest = column[String]("guest")
  def comment = column[String]("comment")
  def * = (id, guest, comment)

}
