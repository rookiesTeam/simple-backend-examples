import os

import peewee
from peewee import CharField, TextField
from peewee import Model
from peewee import SqliteDatabase

DATABASE = 'sample_tornado_guestbook/db/comments.db'
try:
    database = SqliteDatabase(DATABASE)
except Exception as err:
    raise IOError("Exception: open {}\n{}".format(DATABASE, err))


class BaseModel(Model):
    class Meta:
        database = database


class Comment(BaseModel):
    username = CharField()
    content = TextField()

    def serialize(self):
        return {
            'username': self.username,
            'content': self.content
        }
database.connect()
database.drop_table(Comment)
database.create_table(Comment)


@database.atomic()
def create_comment(username: str, content: str) -> Comment:
    return Comment.create(username=username, content=content)


@database.atomic()
def get_comments(limit: int = None, offset: int = None) -> list(Comment):
    comments = Comment.select()
    if limit:
        comments = comments.limit(limit)
    if offset:
        comments = comments.offset(offset)
    return [comment.serialize for comment in comments]
