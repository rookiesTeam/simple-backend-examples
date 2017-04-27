from bottle import Bottle, HTTPResponse, request
from bottle_mongo import MongoPlugin
from bson.json_util import dumps

app = Bottle()
plugin = MongoPlugin(uri="mongodb://127.0.0.1", db="mydb", json_mongo=True)
app.install(plugin)

@app.get('/comments')
def index(mongodb):

    def convert(number):
        return int(number) if number.isdigit() and number[0] != '-' else 0

    limit = convert(request.query.get('limit', '0'))
    offset = convert(request.query.get('offset', '0'))
    return HTTPResponse(status=200,
                        body=dumps(mongodb['collection']
                                   .find({}, {'_id': 0})
                                   .limit(int(limit))
                                   .skip(int(offset))))

@app.post('/comments')
def create(mongodb):
    username = request.forms.get("username")
    content = request.forms.get("content")
    if username and content:
        mongodb['collection'].insert_one({'username': username, 'content': content})
        return HTTPResponse(status=204)
    else:
        return HTTPResponse(status=400, body="Missing username or content")

app.run(host='localhost', port=8000, debug=True)
