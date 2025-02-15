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
  }

  public static class ElevatorConstants {
    public static final int firstStageCANID = 21;
    public static final int secondStageCANID = 22;
  }

  public static class IntakeConstants {
    public static final int intakeCANID = 23;
  }
}
