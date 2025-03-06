package frc.robot.util;

import static frc.robot.Constants.IntakeConstants.kIntakeCenterOffset;
import static frc.robot.subsystems.vision.VisionConstants.aprilTagLayout;
import static frc.robot.util.FieldConstants.*;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.drive.DriveConstants;

public class Branch {

  private final String letter;
  private String station = "Left";
  private int stationID;
  private int branchID;
  private double branchOffset;

  public Branch(String branchLetter) {
    letter = branchLetter.toUpperCase();
    switch (letter) {
      case "A":
        branchID = 18;
        stationID = 13;
        branchOffset = -kBranchOffset;
        break;
      case "B":
        branchID = 18;
        stationID = 13;
        branchOffset = kBranchOffset;
        break;
      case "C":
        branchID = 17;
        stationID = 12;
        branchOffset = -kBranchOffset;
        break;
      case "D":
        branchID = 17;
        stationID = 12;
        branchOffset = kBranchOffset;
        break;
      case "E":
        branchID = 22;
        stationID = 12;
        branchOffset = -kBranchOffset;
        break;
      case "F":
        branchID = 22;
        stationID = 12;
        branchOffset = kBranchOffset;
        break;
      case "G":
        branchID = 21;
        stationID = 13;
        branchOffset = -kBranchOffset;
        break;
      case "H":
        branchID = 21;
        stationID = 13;
        branchOffset = kBranchOffset;
        break;
      case "I":
        branchID = 20;
        stationID = 13;
        branchOffset = -kBranchOffset;
        break;
      case "J":
        branchID = 20;
        stationID = 13;
        branchOffset = kBranchOffset;
        break;
      case "K":
        branchID = 19;
        stationID = 13;
        branchOffset = -kBranchOffset;
        break;
      case "L":
        branchID = 19;
        stationID = 13;
        branchOffset = kBranchOffset;
        break;
    }

    if(DriverStation.getAlliance().get().equals(Alliance.Red)){
      branchID-=11;
      stationID-=11;
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
        stationID = 12;
        if(DriverStation.getAlliance().get().equals(Alliance.Red)){
          branchID-=11;
          stationID-=11;
        }
        break;
    }
  }

  public Pose2d getBranchPose() {

    Pose2d shiftedPose = aprilTagLayout.getTagPose(branchID).get().toPose2d();

    return shiftedPose.transformBy(
        new Transform2d(
            Math.sin(shiftedPose.getRotation().getRadians()) * (kIntakeCenterOffset+branchOffset)
                + Math.cos(shiftedPose.getRotation().getRadians()) * kReefOffset+0.5*DriveConstants.kDriveBaseWidth,
            Math.cos(shiftedPose.getRotation().getRadians()) * (kIntakeCenterOffset+branchOffset)
                + Math.sin(shiftedPose.getRotation().getRadians()) * kReefOffset+0.5*DriveConstants.kDriveBaseWidth,
            new Rotation2d()));
  }

  public Pose2d getStationPose() {
    Pose2d shiftedPose = aprilTagLayout.getTagPose(stationID).get().toPose2d();

    return shiftedPose.transformBy(
        new Transform2d(
            Math.sin(shiftedPose.getRotation().getRadians()) * (kIntakeCenterOffset)
                + Math.cos(shiftedPose.getRotation().getRadians()) * kReefOffset,
            Math.cos(shiftedPose.getRotation().getRadians()) * (kIntakeCenterOffset)
                + Math.sin(shiftedPose.getRotation().getRadians()) * kReefOffset,
            new Rotation2d()));
  }
}
