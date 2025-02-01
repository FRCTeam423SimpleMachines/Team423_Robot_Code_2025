package frc.robot.util;

public class Branch {

  private final String letter;
  private String startPath = "Start to ";
  private String stationPath = "Station from ";

  public Branch(String branchLetter) {
    letter = branchLetter.toUpperCase();
    switch (letter) {
      case "A":
      case "B":
        startPath += "AB";
        stationPath += "AB";
        break;
      case "C":
      case "D":
        startPath += "CD";
        stationPath += "CD";
        break;
      case "E":
      case "F":
        startPath += "EF";
        stationPath += "EF";
        break;
      case "G":
      case "H":
        startPath += "GH";
        stationPath += "GH";
        break;
      case "I":
      case "J":
        startPath += "IJ";
        stationPath += "IJ";
        break;
      default:
        startPath += "KL";
        stationPath += "KL";
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
}
