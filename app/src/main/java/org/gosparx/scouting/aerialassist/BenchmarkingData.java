package org.gosparx.scouting.aerialassist;

public class BenchmarkingData{
    private final int teamNumber;
    private final String eventName;
    private String student;
    private String driveSystem = "";
    private double drivesSpeed = 0;
    private boolean canPlayDefenseBenchButton;
    private boolean abilityToShootHighGoalBenchButton;
    private String typeOfShooterBenchInput = "";
    private double ballsPerSecondBenchInput = 0;
    private int ballsInCycleBenchInput = 0;
    private int cycleTimeHighBenchInput = 0;
    private double shootingRangeBenchInput = 0;
    private String preferredShootingLocationBenchInput = "";
    private double accuracyHighBenchInput = 0;
    private boolean pickupBallHopperBenchButton;
    private boolean pickupBallFloorBenchButton;
    private boolean pickupBallHumanBenchButton;
    private String pickupBallPreferredBenchInput = "";
    private int maximumBallCapacityBenchInput = 0;
    private boolean canScoreGearsBenchButton;
    private boolean pickupGearFloorBenchButton;
    private boolean pickupGearRetrievalBenchButton;
    private String radioPickupGearPreferred = "";
    private boolean canGearLeftBench;
    private boolean canGearCenterBench;
    private boolean canGearRightBench;
    private String radioPreferredGear = "";
    private int cycleTimeGearsBenchInput = 0;
    private boolean abilityToShootLowGoalBenchButton;
    private int cycleTimeLowBenchInput = 0;
    private int cycleNumberLowBenchInput = 0;
    private boolean abilityScaleBenchButton;
    private boolean placesCanScaleRight;
    private boolean placesCanScaleCenter;
    private boolean placesCanScaleLeft;
    private boolean hasActiveGearSystemButton;
    private boolean benchmarkWasDoneButton;
    private String preferredPlacesScaleInput = "";
    private String autoAbilitiesBench = "";
    private String commentsBench = "";
    private String[] driveTypes = {"Swerve", "Mechanum", "Tank Treads",
            "8 wheel traction drive", "6 wheel traction drive", "4 wheel traction drive",
            "8 wheel omni drive", "6 wheel omni drive", "4 wheel omni drive",
            "8 wheel traction + omni drive", "6 wheel traction + omni drive", "4 wheel traction + omni drive",
            "Mechanum traction hyrbid"};

    public BenchmarkingData(int teamNumber, String eventName) {
        this.teamNumber = teamNumber;
        this.eventName = eventName;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {this.student = student;}

    public String getDriveSystem() {
        return driveSystem;
    }

    public void setDriveSystem(String driveSystem) {
        this.driveSystem = driveSystem;
    }

    public double getDrivesSpeed() {
        return drivesSpeed;
    }

    public void setDrivesSpeed(double drivesSpeed) {
        this.drivesSpeed = drivesSpeed;
    }

    public boolean isCanPlayDefenseBenchButton() {
        return canPlayDefenseBenchButton;
    }

    public void setCanPlayDefenseBenchButton(boolean canPlayDefenseBenchButton) {
        this.canPlayDefenseBenchButton = canPlayDefenseBenchButton;
    }

    public boolean isAbilityToShootHighGoalBenchButton() {
        return abilityToShootHighGoalBenchButton;
    }

    public void setAbilityToShootHighGoalBenchButton(boolean abilityToShootHighGoalBenchButton) {
        this.abilityToShootHighGoalBenchButton = abilityToShootHighGoalBenchButton;
    }

    public String getTypeOfShooterBenchInput() {
        return typeOfShooterBenchInput;
    }

    public void setTypeOfShooterBenchInput(String typeOfShooterBenchInput) {
        this.typeOfShooterBenchInput = typeOfShooterBenchInput;
    }

    public double getBallsPerSecondBenchInput() {
        return ballsPerSecondBenchInput;
    }

    public void setBallsPerSecondBenchInput(double ballsPerSecondBenchInput) {
        this.ballsPerSecondBenchInput = ballsPerSecondBenchInput;
    }

    public int getBallsInCycleBenchInput() {
        return ballsInCycleBenchInput;
    }

    public void setBallsInCycleBenchInput(int ballsInCycleBenchInput) {
        this.ballsInCycleBenchInput = ballsInCycleBenchInput;
    }

    public int getCycleTimeHighBenchInput() {
        return cycleTimeHighBenchInput;
    }

    public void setCycleTimeHighBenchInput(int cycleTimeHighBenchInput) {
        this.cycleTimeHighBenchInput = cycleTimeHighBenchInput;
    }

    public double getShootingRangeBenchInput() {
        return shootingRangeBenchInput;
    }

    public void setShootingRangeBenchInput(double shootingRangeBenchInput) {
        this.shootingRangeBenchInput = shootingRangeBenchInput;
    }

    public String getPreferredShootingLocationBenchInput() {
        return preferredShootingLocationBenchInput;
    }

    public void setPreferredShootingLocationBenchInput(String preferredShootingLocationBenchInput) {
        this.preferredShootingLocationBenchInput = preferredShootingLocationBenchInput;
    }

    public double getAccuracyHighBenchInput() {
        return accuracyHighBenchInput;
    }

    public void setAccuracyHighBenchInput(double accuracyHighBenchInput) {
        this.accuracyHighBenchInput = accuracyHighBenchInput;
    }

    public boolean isPickupBallHopperBenchButton() {
        return pickupBallHopperBenchButton;
    }

    public void setPickupBallHopperBenchButton(boolean pickupBallHopperBenchButton) {
        this.pickupBallHopperBenchButton = pickupBallHopperBenchButton;
    }

    public boolean isHasActiveGearSystemButton() {
        return hasActiveGearSystemButton;
    }

    public void setHasActiveGearSystemButton(boolean hasActiveGearSystemButton) {
        this.hasActiveGearSystemButton = hasActiveGearSystemButton;
    }

    public boolean isPickupBallFloorBenchButton() {
        return pickupBallFloorBenchButton;
    }

    public void setPickupBallFloorBenchButton(boolean pickupBallFloorBenchButton) {
        this.pickupBallFloorBenchButton = pickupBallFloorBenchButton;
    }

    public boolean isPickupBallHumanBenchButton() {
        return pickupBallHumanBenchButton;
    }

    public void setPickupBallHumanBenchButton(boolean pickupBallHumanBenchButton) {
        this.pickupBallHumanBenchButton = pickupBallHumanBenchButton;
    }

    public String getPickupBallNoneBenchInput() {
        return pickupBallPreferredBenchInput;
    }

    public void setPickupBallNoneBenchInput(String pickupBallPreferredBenchInput) {
        this.pickupBallPreferredBenchInput = pickupBallPreferredBenchInput;
    }

    public String getPickupBallPreferredBenchInput() {
        return pickupBallPreferredBenchInput;
    }

    public void setPickupBallPreferredBenchInput(String pickupBallPreferredBenchInput) {
        this.pickupBallPreferredBenchInput = pickupBallPreferredBenchInput;
    }

    public int getMaximumBallCapacityBenchInput() {
        return maximumBallCapacityBenchInput;
    }

    public void setMaximumBallCapacityBenchInput(int maximumBallCapacityBenchInput) {
        this.maximumBallCapacityBenchInput = maximumBallCapacityBenchInput;
    }

    public boolean isCanScoreGearsBenchButton() {
        return canScoreGearsBenchButton;
    }

    public void setCanScoreGearsBenchButton(boolean canScoreGearsBenchButton) {
        this.canScoreGearsBenchButton = canScoreGearsBenchButton;
    }

    public boolean isPickupGearFloorBenchButton() {
        return pickupGearFloorBenchButton;
    }

    public void setPickupGearFloorBenchButton(boolean pickupGearFloorBenchButton) {
        this.pickupGearFloorBenchButton = pickupGearFloorBenchButton;
    }

    public boolean isPickupGearRetrievalBenchButton() {
        return pickupGearRetrievalBenchButton;
    }

    public void setPickupGearRetrievalBenchButton(boolean pickupGearRetrievalBenchButton) {
        this.pickupGearRetrievalBenchButton = pickupGearRetrievalBenchButton;
    }

    public String getPickupGearPreferred() {
        return radioPickupGearPreferred;
    }

    public void setPickupGearPreferred(String radioPickupGearPreferred) {
        this.radioPickupGearPreferred = radioPickupGearPreferred;
    }

    public boolean isCanGearLeftBench() {
        return canGearLeftBench;
    }

    public void setCanGearLeftBench(boolean canGearLeftBench) {
        this.canGearLeftBench = canGearLeftBench;
    }

    public boolean isCanGearCenterBench() {
        return canGearCenterBench;
    }

    public void setCanGearCenterBench(boolean canGearCenterBench) {
        this.canGearCenterBench = canGearCenterBench;
    }

    public boolean isCanGearRightBench() {
        return canGearRightBench;
    }

    public void setCanGearRightBench(boolean canGearRightBench) {
        this.canGearRightBench = canGearRightBench;
    }

    public String getRadioPreferredGear() {
        return radioPreferredGear;
    }

    public void setRadioPreferredGear(String radioPreferredGear) {
        this.radioPreferredGear = radioPreferredGear;
    }

    public int getCycleTimeGearsBenchInput() {
        return cycleTimeGearsBenchInput;
    }

    public void setCycleTimeGearsBenchInput(int cycleTimeGearsBenchInput) {
        this.cycleTimeGearsBenchInput = cycleTimeGearsBenchInput;
    }

    public boolean isAbilityToShootLowGoalBenchButton() {
        return abilityToShootLowGoalBenchButton;
    }

    public void setAbilityToShootLowGoalBenchButton(boolean abilityToShootLowGoalBenchButton) {
        this.abilityToShootLowGoalBenchButton = abilityToShootLowGoalBenchButton;
    }

    public int getCycleTimeLowBenchInput() {
        return cycleTimeLowBenchInput;
    }

    public void setCycleTimeLowBenchInput(int cycleTimeLowBenchInput) {
        this.cycleTimeLowBenchInput = cycleTimeLowBenchInput;
    }

    public int getCycleNumberLowBenchInput() {
        return cycleNumberLowBenchInput;
    }

    public void setCycleNumberLowBenchInput(int cycleNumberLowBenchInput) {
        this.cycleNumberLowBenchInput = cycleNumberLowBenchInput;
    }

    public boolean isAbilityScaleBenchButton() {
        return abilityScaleBenchButton;
    }

    public void setAbilityScaleBenchButton(boolean abilityScaleBenchButton) {
        this.abilityScaleBenchButton = abilityScaleBenchButton;
    }

    public boolean isBenchmarkingWasDoneButton() {return benchmarkWasDoneButton;}

    public void setBenchmarkWasDoneButton(boolean benchmarkWasDoneButton) {
        this.benchmarkWasDoneButton = benchmarkWasDoneButton;
    }

    public String getPreferredPlacesScaleInput() {
        return preferredPlacesScaleInput;
    }

    public void setPreferredPlacesScaleInput(String preferredPlacesScaleInput) {
        this.preferredPlacesScaleInput = preferredPlacesScaleInput;
    }

    public String getAutoAbilitiesBench() {
        return autoAbilitiesBench;
    }

    public void setAutoAbilitiesBench(String autoAbilitiesBench) {
        this.autoAbilitiesBench = autoAbilitiesBench;
    }

    public String getCommentsBench() {
        return commentsBench;
    }

    public void setCommentsBench(String commentsBench) {
        this.commentsBench = commentsBench;
    }

    public boolean isPlacesCanScaleRight() {
        return placesCanScaleRight;
    }

    public void setPlacesCanScaleRight(boolean placesCanScaleRight) {
        this.placesCanScaleRight = placesCanScaleRight;
    }

    public boolean isPlacesCanScaleCenter() {
        return placesCanScaleCenter;
    }

    public void setPlacesCanScaleCenter(boolean placesCanScaleCenter) {
        this.placesCanScaleCenter = placesCanScaleCenter;
    }

    public boolean isPlacesCanScaleLeft() {
        return placesCanScaleLeft;
    }

    public void setPlacesCanScaleLeft(boolean placesCanScaleLeft) {
        this.placesCanScaleLeft = placesCanScaleLeft;
    }
}
