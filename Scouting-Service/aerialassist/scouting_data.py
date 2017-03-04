#!/usr/bin/env python

import logging
import webapp2
import json
from aerialassist.model.scouting import Scouting, ScoutingAuto, ScoutingTele, ScoutingGeneral
from aerialassist.model.benchmarkingData import BenchmarkingData
from google.appengine.ext import ndb

class GetScoutingData(webapp2.RequestHandler):
    def getMatchLevel(self, team_key, event_key, match_key):
        scoutingQueryList = Scouting.query(Scouting.teamKey == team_key, Scouting.eventKey == event_key, Scouting.matchKey == match_key)
        
        scoutingList = scoutingQueryList.map(self.callback)
        
        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))
    
    def getEventLevel(self, team_key, event_key):
        scoutingQueryList = Scouting.query(Scouting.teamKey == team_key, Scouting.eventKey == event_key)
        
        scoutingList = scoutingQueryList.map(self.callback)
        
        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))

    def getBenchmarkingEventLevel(self, team_key, event_key):
        scoutingQueryList = ScoutingInfo.query(ScoutingInfo.teamKey == team_key, ScoutingInfo.eventKey == event_key)

        scoutingList = scoutingQueryList.map(self.callback)

        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))
    
    def getTeamLevel(self, team_key):
        scoutingQueryList = Scouting.query(Scouting.teamKey == team_key)
        
        scoutingList = scoutingQueryList.map(self.callback)
        
        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))

    def getAll(self):
        scoutingQueryList = Scouting.query()

        scoutingList = scoutingQueryList.map(self.callback)

        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))

    def getBenchmarkingAll(self):
        scoutingQueryList = ScoutingInfo.query()

        scoutingList = scoutingQueryList.map(self.callback)

        self.response.content_type = 'application/json'
        self.response.write(json.dumps(scoutingList))


    def callback(self, scouting):
        return scouting.to_dict()

class PostScoutingData(webapp2.RequestHandler):
    def post(self):
        logging.info('%s',self.request.body) 
        jsonRoot = json.loads(self.request.body)
        scouting = Scouting()
        scouting.populate(**jsonRoot)
        scouting.key = ndb.Key(Scouting, scouting.teamKey + '-' + scouting.eventKey + '-' + scouting.matchKey + '-' + scouting.nameOfScouter)
        scouting.put()

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