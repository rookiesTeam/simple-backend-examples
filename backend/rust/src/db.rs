extern crate diesel;

use super::*;
use super::models::*;
use self::diesel::prelude::*;

pub fn get_comments(limit: i64, offset: i64) -> QueryResult<Vec<Comment>> {
    use super::schema::comments::dsl::*;

    let connection = establish_connection();
    if limit != -1 && offset != -1{
        comments
        .limit(limit)
        .offset(offset)
        .load::<Comment>(&connection)
    } else if limit != -1 {
        comments
        .limit(limit)
        .load::<Comment>(&connection)
    } else if offset != -1 {
        comments
        .offset(offset)
        .load::<Comment>(&connection)
    } else {
        comments.load::<Comment>(&connection)
    }
}

pub fn insert_comment(new_comment: NewComment) -> QueryResult<Comment>{
    use super::schema::comments;
    let connection = establish_connection();
    diesel::insert(&new_comment).into(comments::table)
        .get_result(&connection)
}
