#!/usr/bin/env python

import logging
import webapp2
import json
import base64
from aerialassist.model.benchmarkingData import BenchmarkingData
from aerialassist.model.scoutingData import ScoutingData
from aerialassist.model.robotImage import RobotImage
from google.appengine.ext import ndb

class PostBenchmarkingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        benchmarkingData = BenchmarkingData()
        benchmarkingData.populate(**jsonRoot)
        benchmarkingData.key = ndb.Key(BenchmarkingData, benchmarkingData.teamNumber)
        benchmarkingData.put()
        self.response.status = 200

class PostScoutingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body)
        jsonRoot = json.loads(self.request.body)
        scoutingData = ScoutingData()
        scoutingData.populate(**jsonRoot)
        scoutingData.key = ndb.Key(ScoutingData, scoutingData.teamNumber)
        scoutingData.put()
        self.response.status = 200

class PostPictures(webapp2.RequestHandler):
    def post(self):
        file_upload = self.request.POST.get("robot_picture", None)
        file_name = file_upload.filename
        robotImage = RobotImage(id=file_name, file_name=file_name, blob=base64.b64encode(file_upload.file.read()))
        robotImage.put()
        self.response.status = 200

class GetBenchmarking(webapp2.RequestHandler):
    def getBenchmarking(self):
        benchmarkingQueryList = BenchmarkingData.query()
        benchmarkingList = benchmarkingQueryList.map(self.callback)
        self.response.content_type = 'application/json'
        self.response.write(json.dumps(benchmarkingList))

    def callback(self, cb):
        return cb.to_dict()

class GetPictures(webapp2.RequestHandler):
    def getPictures(self):
        picturesQueryList = RobotImage.query()
        picturesList = picturesQueryList.map(self.callback)
        #data = {'name': 'test',
        #    'photo': base64.b64encode(picturesList)}
        self.response.content_type = 'application/json'
        #self.response.write(json.dumps(picturesList, ensure_ascii=False, encoding='base64'))
        self.response.write(json.dumps(picturesList))

    def callback(self, cb):
        return cb.to_dict()

class GetScouting(webapp2.RequestHandler):
    def getScouting(self):
        scoutingQueryList = ScoutingData.query()
        scoutingList = scoutingQueryList.map(self.callback)
        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))

    def callback(self, cb):
        return cb.to_dict()