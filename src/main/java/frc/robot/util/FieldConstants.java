package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {
  // Poses for each branch of the reef
  public static final Pose2d kBranchA =
      new Pose2d(3.266, 4.187, new Rotation2d(Units.degreesToRadians(180.0)));
  public static final Pose2d kBranchB =
      new Pose2d(3.266, 3.860, new Rotation2d(Units.degreesToRadians(180.0)));
  public static final Pose2d kBranchC =
      new Pose2d(3.725, 3.040, new Rotation2d(Units.degreesToRadians(-1200.0)));
  public static final Pose2d kBranchD =
      new Pose2d(4.007, 2.894, new Rotation2d(Units.degreesToRadians(-120.0)));
  public static final Pose2d kBranchE =
      new Pose2d(4.953, 2.874, new Rotation2d(Units.degreesToRadians(-60.0)));
  public static final Pose2d kBranchF =
      new Pose2d(5.245, 3.050, new Rotation2d(Units.degreesToRadians(-60.0)));
  public static final Pose2d kBranchG =
      new Pose2d(5.713, 3.860, new Rotation2d(Units.degreesToRadians(0.0)));
  public static final Pose2d kBranchH =
      new Pose2d(5.713, 4.187, new Rotation2d(Units.degreesToRadians(0.0)));
  public static final Pose2d kBranchI =
      new Pose2d(5.713, 3.860, new Rotation2d(Units.degreesToRadians(60.0)));
  public static final Pose2d kBranchJ =
      new Pose2d(4.963, 5.156, new Rotation2d(Units.degreesToRadians(60.0)));
  public static final Pose2d kBranchK =
      new Pose2d(4.017, 5.146, new Rotation2d(Units.degreesToRadians(120.0)));
  public static final Pose2d kBranchL =
      new Pose2d(3.744, 5.000, new Rotation2d(Units.degreesToRadians(120.0)));

  // Poses for both stations
  public static final Pose2d kLeftStation =
      new Pose2d(1.103, 7.064, new Rotation2d(Units.degreesToRadians(-55.0)));
  public static final Pose2d kRightStation =
      new Pose2d(1.103, 1.003, new Rotation2d(Units.degreesToRadians(-55.0)));
}
