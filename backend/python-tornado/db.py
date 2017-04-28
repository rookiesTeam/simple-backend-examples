import os
import sys

import peewee
from peewee import CharField, TextField
from peewee import Model
from peewee import SqliteDatabase

DATABASE = 'db/comments.db'
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

def drop_database():
    try:
        database.drop_table(Comment)
    except Exception as err:
        print("Table comments does not exits", file=sys.stderr)

def create_database():
    try:
        database.create_table(Comment)
    except Exception as err:
        print("Table comments already exits", file=sys.stderr)

def reset_database():
    drop_database()
    create_database()

create_database()

@database.atomic()
def create_comment(username: str, content: str) -> Comment:
    return Comment.create(username=username, content=content)


@database.atomic()
def get_comments(limit: int = -1, offset: int = 0) -> list(Comment):
    comments = Comment.select().limit(limit).offset(offset)
    return [comment.serialize() for comment in comments]
