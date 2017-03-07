#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
from aerialassist.scouting_data import PostBenchmarkingData, PostScoutingData, GetBenchmarking

class MainHandler(webapp2.RequestHandler):
    def get(self):
        self.response.write('2017 Sparx 1126 Scouting Service!')

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    webapp2.Route(r'/api/2017/v1/BenchmarkingData', PostBenchmarkingData, methods=['POST']),
    webapp2.Route(r'/api/2017/v1/ScoutingData', PostScoutingData, methods=['POST']),
    webapp2.Route(r'/api/2016/v1/BenchmarkingData/<eventName>',
                  GetBenchmarking,
                  methods=['GET'],
                  handler_method='getBenchmarking')
], debug=True)