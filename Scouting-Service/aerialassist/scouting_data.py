#!/usr/bin/env python

import logging
import webapp2
import json
from aerialassist.model.benchmarkingData import BenchmarkingData
from aerialassist.model.scoutingData import ScoutingData
from google.appengine.ext import ndb

class PostScoutingData(webapp2.RequestHandler):
   def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        scoutinginfo = ScoutingData()
        scoutinginfo.populate(**jsonRoot)
        scoutinginfo.key = ndb.Key(ScoutingData, scoutinginfo.eventName)
        scoutinginfo.put()

        self.response.status = 200

class PostBenchmarkingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        scoutinginfo = BenchmarkingData()
        scoutinginfo.populate(**jsonRoot)
        scoutinginfo.key = ndb.Key(BenchmarkingData, scoutinginfo.eventName)
        scoutinginfo.put()

        self.response.status = 200