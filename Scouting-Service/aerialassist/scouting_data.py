#!/usr/bin/env python

import logging
import webapp2
import json
from aerialassist.model.benchmarkingData import BenchmarkingData
from aerialassist.model.scoutingData import ScoutingData
from google.appengine.ext import ndb

class PostBenchmarkingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        benchmarkingData = BenchmarkingData()
        benchmarkingData.populate(**jsonRoot)
        benchmarkingData.key = ndb.Key(BenchmarkingData, benchmarkingData.eventName)
        benchmarkingData.put()

        self.response.status = 200

class PostScoutingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        scoutingdata = ScoutingData()
        scoutingdata.populate(**jsonRoot)
        scoutingdata.key = ndb.Key(ScoutingData, scoutingdata.eventName)
        scoutingdata.put()

        self.response.status = 200