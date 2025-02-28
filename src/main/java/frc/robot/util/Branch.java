package frc.robot.util;

import static frc.robot.Constants.IntakeConstants.kIntakeCenterOffset;
import static frc.robot.util.FieldConstants.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

public class Branch {

  private final String letter;
  private Pose2d branchPose;
  private Pose2d stationPose;

  public Branch(String branchLetter) {
    letter = branchLetter.toUpperCase();
    switch (letter) {
      case "A":
        branchPose = kBranchA;
        stationPose = kLeftStation;
        break;
      case "B":
        branchPose = kBranchB;
        stationPose = kLeftStation;
        break;
      case "C":
        branchPose = kBranchC;
        stationPose = kRightStation;
        break;
      case "D":
        branchPose = kBranchD;
        stationPose = kRightStation;
        break;
      case "E":
        branchPose = kBranchE;
        stationPose = kRightStation;
        break;
      case "F":
        branchPose = kBranchF;
        stationPose = kRightStation;
        break;
      case "G":
        branchPose = kBranchG;
        stationPose = kLeftStation;
        break;
      case "H":
        branchPose = kBranchH;
        stationPose = kLeftStation;
        break;
      case "I":
        branchPose = kBranchI;
        stationPose = kLeftStation;
        break;
      case "J":
        branchPose = kBranchJ;
        stationPose = kLeftStation;
        break;
      case "K":
        branchPose = kBranchK;
        stationPose = kLeftStation;
        break;
      case "L":
        branchPose = kBranchL;
        stationPose = kLeftStation;
        break;
    }
  }

  public String getLetter() {
    return letter;
  }

  public void setRightStationPreference() {
    switch (letter) {
      case "A":
      case "B":
      case "H":
      case "G":
        stationPose = kRightStation;
        break;
    }
  }

  public Pose2d getBranchPose() {
    return branchPose.transformBy(
        new Transform2d(
            Math.sin(branchPose.getRotation().getRadians()) * kIntakeCenterOffset,
            Math.cos(branchPose.getRotation().getRadians()) * kIntakeCenterOffset,
            new Rotation2d()));
  }

  public Pose2d getStationPose() {
    return stationPose;
  }
}
