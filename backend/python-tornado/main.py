import json

import sys
import tornado.ioloop
import tornado.web

from sample_tornado_guestbook.db import create_comment
from sample_tornado_guestbook.db import get_comments


class MainHandler(tornado.web.RequestHandler):

    def prepare(self):
        if self.request.headers.get("Content-Type", "").startswith("application/json"):
            self.json_args = json.loads(self.request.body.decode("utf-8"))
        else:
            self.json_args = None

    def get(self):
        comments = get_comments(limit=self.get_query_argument('limit', None),
                                offset=self.get_query_argument('offset', None))
        self.write({'comments': comments})

    def post(self):
        try:
            comment = create_comment(username=self.json_args['nombre'],
                                     content=self.json_args['texto'])
            self.write({'status': 'ok', 'comment': comment.serialize()})
        except Exception as err:
            self.write({'status': 'error', 'reason': str(err)})

if __name__ == '__main__':
    application = tornado.web.Application([
        (r"/comments", MainHandler),
    ])
    application.listen(8000)
    tornado.ioloop.IOLoop.current().start()
