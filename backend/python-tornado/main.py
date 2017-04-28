import json

import sys
import tornado.ioloop
import tornado.web

from db import create_comment
from db import get_comments


class MainHandler(tornado.web.RequestHandler):

    def prepare(self):
        if self.request.headers.get("Content-Type", "").startswith("application/json"):
            self.json_args = json.loads(self.request.body.decode("utf-8"))
        else:
            self.json_args = None

    def get(self):
        try:
            limit = int(self.get_query_argument('limit', -1))
            offset = int(self.get_query_argument('offset', 0))
            print(limit, offset, file=sys.stderr)
            comments = get_comments(limit=limit,
                                offset=offset)
            self.write(json.dumps(comments))
        except Exception as err:
            self.write({'status': 'err', 'reason': "Error {0}".format(str(err))})

    def post(self):
        try:
            comment = create_comment(username=self.json_args['nombre'],
                                     content=self.json_args['texto'])
            self.write({'status': 'ok', 'comment': comment.serialize()})
        except Exception as err:
            self.write({'status': 'error', 'reason': str(err)})

if __name__ == '__main__':
    port=8000
    application = tornado.web.Application([
        (r"/comments", MainHandler),
    ])
    application.listen(port)
    print("Server start at port {0}".format(port))
    tornado.ioloop.IOLoop.current().start()
