# Run from GAE remote API:
# 	{GAE Path}\remote_api_shell.py -s {YourAPPName}.appspot.com
# 	import export_as_csv

import csv
from benchmarkingData import BenchmarkingData

def exportToCsv(query, csvFileName, delimiter):
	with open(csvFileName, 'wb') as csvFile:
		csvWriter = csv.writer(csvFile, delimiter=delimiter, quotechar='|', quoting=csv.QUOTE_MINIMAL)
		writeHeader(csvWriter)

		rowsPerQuery = 1000
		totalRowsSaved = 0
		cursor = None
		areMoreRows = True

		while areMoreRows:
			if cursor is not None:
				query.with_cursor(cursor)
			items = query.fetch(rowsPerQuery)
			cursor = query.cursor()

			currentRows =0
			for item in items:
				saveItem(csvWriter, item)
				currentRows += 1

			totalRowsSaved += currentRows
			areMoreRows = currentRows >= rowsPerQuery
			print 'Saved ' + str(totalRowsSaved) + ' rows'

		print 'Finished saving all rows.'

def writeHeader(csvWriter):
	csvWriter.writerow(['teamNumber', 'eventName', 'student', 'driveSystem', 'drivesSpeed', 'canPlayDefenseBenchButton', 'abilityToShootHighGoalBenchButton', 'typeOfShooterBenchInput', 'ballsPerSecondBenchInput', 'ballsInCycleBenchInput', 'cycleTimeHighBenchInput', 'shootingRangeBenchInput', 'preferredShootingLocationBenchInput', 'accuracyHighBenchInput', 'pickupBallHopperBenchButton', 'pickupBallFloorBenchButton', 'pickupBallHumanBenchButton', 'pickupBallPreferredBenchInput', 'maximumBallCapacityBenchInput', 'canScoreGearsBenchButton', 'pickupGearFloorBenchButton', 'pickupGearRetrievalBenchButton', 'radioPickupGearPreferred', 'canGearLeftBench', 'canGearCenterBench', 'canGearRightBench', 'radioPreferredGear', 'cycleTimeGearsBenchInput', 'abilityToShootLowGoalBenchButton', 'cycleTimeLowBenchInput', 'cycleNumberLowBenchInput', 'abilityScaleBenchButton', 'placesCanScaleRight', 'placesCanScaleCenter', 'placesCanScaleLeft', 'benchmarkWasDoneButton', 'preferredPlacesScaleInput', 'autoAbilitiesBench', 'commentsBench']) #Output csv header

def saveItem(csvWriter, item):
	csvWriter.writerow([item.teamNumber, item.eventName, item.student, item.driveSystem, item.drivesSpeed, item.canPlayDefenseBenchButton, item.abilityToShootHighGoalBenchButton, item.typeOfShooterBenchInput, item.ballsPerSecondBenchInput, item.ballsInCycleBenchInput, item.cycleTimeHighBenchInput, item.shootingRangeBenchInput, item.preferredShootingLocationBenchInput, item.accuracyHighBenchInput, item.pickupBallHopperBenchButton, item.pickupBallFloorBenchButton, item.pickupBallHumanBenchButton, item.pickupBallPreferredBenchInput, item.maximumBallCapacityBenchInput, item.canScoreGearsBenchButton, item.pickupGearFloorBenchButton, item.pickupGearRetrievalBenchButton, item.radioPickupGearPreferred, item.canGearLeftBench, item.canGearCenterBench, item.canGearRightBench, item.radioPreferredGear, item.cycleTimeGearsBenchInput, item.abilityToShootLowGoalBenchButton, item.cycleTimeLowBenchInput, item.cycleNumberLowBenchInput, item.abilityScaleBenchButton, item.placesCanScaleRight, item.placesCanScaleCenter, item.placesCanScaleLeft, item.benchmarkWasDoneButton, item.preferredPlacesScaleInput, item.autoAbilitiesBench, item.commentsBench]) # Save items in preferred format


query = BenchmarkingData.gql("ORDER BY teamNumber") #Query for items
exportToCsv(query, 'myExport.csv', ',')
