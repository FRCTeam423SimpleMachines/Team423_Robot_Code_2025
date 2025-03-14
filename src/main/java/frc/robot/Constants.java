// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static class ControlConstants {
    public static final int kLeftXAxis = 0;
    public static final int kLeftYAxis = 1;
    public static final int kRightXAxis = 4;
    public static final int kRightYAxis = 5;

    public static final int kLeftTrigger = 2;
    public static final int kRightTrigger = 3;

    public static final int kAButton = 1;
    public static final int kBButton = 2;
    public static final int kXButton = 3;
    public static final int kYButton = 4;
    public static final int kLeftBumper = 5;
    public static final int kRightBumper = 6;
    public static final int kBackButton = 7;
    public static final int kStartButton = 8;
    public static final int kLeftStickButton = 9;
    public static final int kRightStickButton = 10;

    public static final int kControllerPort1 = 0;
    public static final int kControllerPort2 = 1;

    public static final int kXAxis = 0;
    public static final int kYAxis = 1;

    public static final int kTrigger = 1;
    public static final int kDownButton = 2;
    public static final int kMidButton = 3;
    public static final int kLeftButton = 4;
    public static final int kRightButton = 5;
    public static final int kFrontLeftButton = 6;
    public static final int kMidLeftButton = 7;
    public static final int kBackLeftButton = 8;
    public static final int kBackRightButton = 9;
    public static final int kMidRightButton = 10;
    public static final int kFrontRightButton = 11;
  }

  public static class ElevatorConstants {
    public static final int kFirstStageCANID = 21;
    public static final int kSecondStageCANID = 22;
    public static final int kFirstLaserCANID = 8;
    public static final int kSecondLaserCANID = 9;
    // public static final int kFirstPotChannel = 0;
    // public static final int kSecondPotChanel = 1;
    // public static final double firstPotOffset = -0.007;
    // public static final double secondPotOffset = -1.282;
    // public static final double firstPotToInchesFactor = 26.375 / 0.801;
    // public static final double secondPotToInchesFactor = 24.875 / 0.78;
    public static final double firstLaserOffset = 1.3;
    public static final double secondLaserOffset = 7.5;
    public static final double firstUpperBound = 25;
    public static final double firstLowerBound = 0.1;
    public static final double secondUpperBound = 24;
    public static final double secondLowerBound = 0.1;
    public static final double floorOffset = 5;
    public static final double armOffset = 14;
    public static final TrapezoidProfile kFirstProfile =
        new TrapezoidProfile(new Constraints(0.0, 0.0));
  }

  public static class IntakeConstants {
    public static final int kIntakeCANID = 24;
    public static final int kOpticChannel = 0;
    public static final double kIntakeCenterOffset = Units.inchesToMeters(5);
  }

  public static class LiftConstants {
    public static final int kLiftCANID = 23;
  }

  public static class PivotConstants {
    public static final int kPivotCANID = 25;
  }

  public static class LightConstants {
    public static final double kRed = 0.61;
    public static final double kGreen = 0.77;
    public static final double kBlue = 0.87;
    public static final double kOff = 0.99;
  }
}
