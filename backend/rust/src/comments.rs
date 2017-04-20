use std::string::String;
use rocket_contrib::{JSON,Value};
use sample_rust_server::*;

#[derive(Debug,FromForm)]
struct CommentParams{
    limit: Option<i64>,
    offset: Option<i64>
}

#[get("/")]
fn all() -> JSON<Value> {
    match db::get_comments(-1, -1) {
        Ok(comments) => JSON(json!({"comments": comments})),
        Err(err) => JSON(json!({ "status": "error", "reason": format!("{0}", err) }))
    }
}

#[get("/?<params>")]
fn all_params(params: CommentParams) -> JSON<Value> {
    let limit: i64 = if let Some(limitp) = params.limit {limitp} else {-1};
    let offset: i64 = if let Some(offsetp) = params.offset {offsetp} else {-1};

    match db::get_comments(limit, offset) {
        Ok(comments) => JSON(json!({"comments": comments})),
        Err(err) => JSON(json!({ "status": "error", "reason": format!("{0}", err) }))
    }
}

#[derive(FromForm, Debug)]
struct CommentForm{
    username: String,
    comment: String
}

#[post("/", format="application/json", data="<input>")]
fn new(input: JSON<models::NewComment>) -> JSON<Value> {
    let comment = input.0;
    println!("{:?}", comment);
    match db::insert_comment(comment) {
        Ok(_) => JSON(json!({ "status": "ok" })),
        Err(err) => JSON(json!({ "status": "error", "reason": format!("{0}", err) }))
    }

}
