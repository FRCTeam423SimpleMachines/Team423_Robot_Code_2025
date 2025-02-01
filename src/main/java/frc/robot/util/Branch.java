package frc.robot.util;

import static frc.robot.util.FieldConstants.*;

import edu.wpi.first.math.geometry.Pose2d;

public class Branch {

  private final String letter;
  private String startPath = "Start to ";
  private String stationPath = "Station from ";
  private Pose2d branchPose;

  public Branch(String branchLetter) {
    letter = branchLetter.toUpperCase();
    switch (letter) {
      case "A":
      case "B":
        startPath += "AB";
        stationPath += "AB";
        branchPose = FaceAB;
        break;
      case "C":
      case "D":
        startPath += "CD";
        stationPath += "CD";
        branchPose = FaceCD;
        break;
      case "E":
      case "F":
        startPath += "EF";
        stationPath += "EF";
        branchPose = FaceEF;
        break;
      case "G":
      case "H":
        startPath += "GH";
        stationPath += "GH";
        branchPose = FaceGH;
        break;
      case "I":
      case "J":
        startPath += "IJ";
        stationPath += "IJ";
        branchPose = FaceIJ;
        break;
      default:
        startPath += "KL";
        stationPath += "KL";
        branchPose = FaceKL;
        break;
    }
  }

  public String getLetter() {
    return letter;
  }

  public String getStartPath() {
    return startPath;
  }

  public String getStationPath() {
    return stationPath;
  }

  public void setRightStationPreference() {
    switch (letter) {
      case "A":
      case "B":
      case "H":
      case "G":
        stationPath += " Right";
        break;
    }
  }

  public Pose2d getFacePose() {
    return branchPose;
  }
}
