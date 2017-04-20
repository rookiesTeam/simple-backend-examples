use super::schema::comments;

#[derive(Queryable, Serialize)]
pub struct Comment {
    pub id: i32,
    pub username: String,
    pub content: String,
}

#[derive(Insertable, Debug, Deserialize)]
#[table_name="comments"]
pub struct NewComment {
    pub username: String,
    pub content: String,
}
