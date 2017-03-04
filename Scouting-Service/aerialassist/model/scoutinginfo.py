from google.appengine.ext import ndb

class ScoutingInfoTTWOO(ndb.Model):
    eventName = ndb.StringProperty()
    teamNumber = ndb.IntegerProperty()