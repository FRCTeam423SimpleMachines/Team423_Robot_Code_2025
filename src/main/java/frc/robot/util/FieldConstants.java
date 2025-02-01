package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {
  public static final Pose2d FaceAB =
      new Pose2d(2.817, 4.031, new Rotation2d(Units.degreesToRadians(0.0)));
  public static final Pose2d FaceCD =
      new Pose2d(3.584, 2.64, new Rotation2d(Units.degreesToRadians(60.0)));
  public static final Pose2d FaceEF =
      new Pose2d(5.311, 2.568, new Rotation2d(Units.degreesToRadians(120.0)));
  public static final Pose2d FaceGH =
      new Pose2d(6.162, 4.043, new Rotation2d(Units.degreesToRadians(180.0)));
  public static final Pose2d FaceIJ =
      new Pose2d(5.323, 5.458, new Rotation2d(Units.degreesToRadians(-1200.0)));
  public static final Pose2d FaceKL =
      new Pose2d(3.728, 5.434, new Rotation2d(Units.degreesToRadians(-60.0)));
}
