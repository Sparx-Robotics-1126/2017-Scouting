from google.appengine.ext import ndb

class RobotImage(ndb.Model):
    file_name = ndb.StringProperty()
    blob = ndb.BlobProperty()