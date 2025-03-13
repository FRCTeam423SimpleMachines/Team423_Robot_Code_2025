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

import static frc.robot.Constants.ControlConstants.*;
import static frc.robot.Constants.LightConstants.*;
import static frc.robot.subsystems.drive.DriveConstants.*;
import static frc.robot.subsystems.vision.VisionConstants.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.AutoScoringFixed;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.ElevatorPivotCommand;
import frc.robot.commands.PivotToPositionAuto;
import frc.robot.commands.RunElevatorPosAuto;
import frc.robot.commands.RunIntakeIn;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSpark;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.elevator.ElevatorIOSpark;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.intake.IntakeIOSpark;
import frc.robot.subsystems.lift.Lift;
import frc.robot.subsystems.lift.LiftIO;
import frc.robot.subsystems.lift.LiftIOSim;
import frc.robot.subsystems.lift.LiftIOSpark;
import frc.robot.subsystems.lights.Lights;
import frc.robot.subsystems.lights.LightsIO;
import frc.robot.subsystems.lights.LightsIOReal;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotIO;
import frc.robot.subsystems.pivot.PivotIOSim;
import frc.robot.subsystems.pivot.PivotIOSpark;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import frc.robot.util.Branch;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;
  private final Vision vision;
  private final Elevator elevator;
  private final Intake intake;
  private final Lift lift;
  private final Pivot pivot;
  private final Lights lights;

  // Controller
  private final CommandJoystick controller1 = new CommandJoystick(0);
  private final CommandJoystick controller2 = new CommandJoystick(1);
  private final CommandJoystick stick1 = new CommandJoystick(2);
  private final CommandJoystick stick2 = new CommandJoystick(3);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;
  private final LoggedDashboardChooser<String> stationChooser;
  private final LoggedDashboardChooser<Branch> branchChooser1;
  private final LoggedDashboardChooser<Branch> branchChooser2;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIONavX(),
                new ModuleIOSpark(0),
                new ModuleIOSpark(1),
                new ModuleIOSpark(2),
                new ModuleIOSpark(3));
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIOPhotonVision(cameraFrontLeftName, robotToCameraFrontLeft),
                new VisionIOPhotonVision(cameraFrontRightName, robotToCameraFrontRight),
                new VisionIOPhotonVision(cameraBackLeftName, robotToCameraBackLeft),
                new VisionIOPhotonVision(cameraBackRightName, robotToCameraBackRight));

        elevator = new Elevator(new ElevatorIOSpark());
        intake = new Intake(new IntakeIOSpark());
        lift = new Lift(new LiftIOSpark());
        pivot = new Pivot(new PivotIOSpark());
        lights = new Lights(new LightsIOReal());

        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim());
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIOPhotonVisionSim(
                    cameraFrontLeftName, robotToCameraFrontLeft, drive::getPose),
                new VisionIOPhotonVisionSim(
                    cameraFrontRightName, robotToCameraFrontRight, drive::getPose),
                new VisionIOPhotonVisionSim(
                    cameraBackLeftName, robotToCameraBackLeft, drive::getPose),
                new VisionIOPhotonVisionSim(
                    cameraBackRightName, robotToCameraBackRight, drive::getPose));

        elevator = new Elevator(new ElevatorIOSim());
        intake = new Intake(new IntakeIOSim());
        lift = new Lift(new LiftIOSim());
        pivot = new Pivot(new PivotIOSim());
        lights = new Lights(new LightsIO() {});

        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        vision =
            new Vision(
                drive::addVisionMeasurement,
                new VisionIO() {},
                new VisionIO() {},
                new VisionIO() {},
                new VisionIO() {});

        elevator = new Elevator(new ElevatorIO() {});
        intake = new Intake(new IntakeIO() {});
        lift = new Lift(new LiftIO() {});
        pivot = new Pivot(new PivotIO() {});
        lights = new Lights(new LightsIO() {});
        break;
    }

    NamedCommands.registerCommand("ElevatorL4", new RunElevatorPosAuto(elevator, 77.0));
    NamedCommands.registerCommand("PivotScore", new PivotToPositionAuto(pivot, 340));
    NamedCommands.registerCommand("IntakeOut", new RunCommand(() -> intake.setSpeed(1.0), intake));

    stationChooser = new LoggedDashboardChooser<>("Station Preference");

    stationChooser.addDefaultOption("Left", "Left");
    stationChooser.addOption("Right", "Right");

    branchChooser1 = new LoggedDashboardChooser<>("1st Branch");
    branchChooser2 = new LoggedDashboardChooser<>("2nd Branch");

    final String[] branches = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    branchChooser1.addDefaultOption("A", new Branch("A"));
    branchChooser2.addDefaultOption("A", new Branch("A"));
    for (int i = 1; i < 12; i++) {
      branchChooser1.addOption(branches[i], new Branch(branches[i]));
      branchChooser2.addOption(branches[i], new Branch(branches[i]));
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    autoChooser.addOption("AutoScoring", new AutoScoringFixed(drive, elevator, pivot, intake));

    try {
      autoChooser.addOption(
          "pathfinding test",
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile("Score G"), kDefaultConstraints));
    } catch (FileVersionException | IOException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive

    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> MathUtil.applyDeadband(-stick1.getRawAxis(kYAxis), 0.06),
            () -> MathUtil.applyDeadband(-stick1.getRawAxis(kXAxis), 0.06),
            () -> MathUtil.applyDeadband(-stick2.getRawAxis(kXAxis), 0.06)));

    // drive.setDefaultCommand(
    //     DriveCommands.joystickDrive(
    //         drive,
    //         () -> -controller1.getRawAxis(kLeftYAxis),
    //         () -> -controller1.getRawAxis(kLeftXAxis),
    //         () -> -controller1.getRawAxis(kRightXAxis)));

    elevator.setDefaultCommand(new RunCommand(() -> elevator.runBoth(0.0, 0.0), elevator));

    lights.setDefaultCommand(new RunCommand(() -> lights.setValue(kOff), lights));

    intake.setDefaultCommand(new RunCommand(() -> intake.setSpeed(0.0), intake));

    pivot.setDefaultCommand(new RunCommand(() -> pivot.runPow(0.0), pivot));

    lift.setDefaultCommand(
        new RunCommand(() -> lift.run(-controller2.getRawAxis(kLeftYAxis)), lift));

    // Lock to 0° when A button is held
    controller1
        .button(kAButton)
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> controller1.getRawAxis(kLeftYAxis),
                () -> controller1.getRawAxis(kLeftXAxis),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    stick1.button(kMidButton).onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    controller1
        .button(kBButton)
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    controller1
        .button(kRightBumper)
        .whileTrue(
            DriveCommands.joystickDriveRobotRelative(
                drive,
                () -> -controller1.getRawAxis(kLeftYAxis),
                () -> -controller1.getRawAxis(kLeftXAxis),
                () -> 0.0));

    // stick1
    //     .button(kRightBumper)
    //     .whileTrue(
    //         DriveCommands.joystickDriveRobotRelative(
    //             drive,
    //             () -> -stick1.getRawAxis(kYAxis),
    //             () -> -stick1.getRawAxis(kXAxis),
    //             () -> 0.0));

    // stick1
    //     .button(kFrontLeftButton)
    //     .whileTrue(
    //         new RunCommand(
    //             () ->
    //                 elevator.runBoth(
    //                     MathUtil.applyDeadband(-stick1.getRawAxis(kYAxis), 0.06),
    //                     MathUtil.applyDeadband(-stick2.getRawAxis(kYAxis), 0.06)),
    //             elevator));

    // stick1.button(kDownButton).onTrue(new RunIntakeIn(intake, -0.7));

    // stick1.button(kMidButton).whileTrue(new RunCommand(() -> intake.setSpeed(1.0), intake));

    // stick2
    //     .button(kBackLeftButton)
    //     .onTrue(new ElevatorPivotCommand(elevator, pivot, 28.0, 30)); // Station

    // stick2
    //     .button(kBackRightButton)
    //     .onTrue(new ElevatorPivotCommand(elevator, pivot, 53.0, 340)); // L3

    controller2
        .axisGreaterThan(kLeftTrigger, 0.75)
        .whileTrue(
            new RunCommand(
                () ->
                    elevator.runBoth(
                        MathUtil.applyDeadband(-controller2.getRawAxis(kRightYAxis), 0.06),
                        MathUtil.applyDeadband(-controller2.getRawAxis(kLeftYAxis), 0.06)),
                elevator));

    controller2.button(kRightBumper).onTrue(new RunIntakeIn(intake, -0.7));

    controller2.button(kLeftBumper).whileTrue(new RunCommand(() -> intake.setSpeed(1.0), intake));

    controller2
        .button(kAButton)
        .onTrue(new ElevatorPivotCommand(elevator, pivot, 28.0, 26)); // Station

    controller2.button(kBButton).onTrue(new ElevatorPivotCommand(elevator, pivot, 53.0, 330)); // L3

    controller2
        .axisGreaterThan(kRightTrigger, 0.75)
        .onTrue(new ElevatorPivotCommand(elevator, pivot, 37.0, 330)); // L2

    controller2
        .button(kXButton)
        .onTrue(new ElevatorPivotCommand(elevator, pivot, 28.0, 90.0)); // Reset

    controller2.button(kYButton).onTrue(new ElevatorPivotCommand(elevator, pivot, 78.0, 330)); // L4

    controller2.povUp().whileTrue(new RunCommand(() -> pivot.runPow(0.3), intake));

    controller2.povDown().whileTrue(new RunCommand(() -> pivot.runPow(-0.3), intake));

    // stick2
    //     .button(kFrontRightButton)
    //     .onTrue(new ElevatorPivotCommand(elevator, pivot, 77.0, 340)); // L4

    // stick1.button(kFrontRightButton).whileTrue(new RunCommand(() -> pivot.runPow(0.3), intake));

    // stick1.button(kMidRightButton).whileTrue(new RunCommand(() -> pivot.runPow(-0.3), intake));

    // controller2.button(kAButton).onTrue(new RunElevatorPos(elevator, 48.0));

    // controller2.button(kBButton).whileTrue(new RunCommand(() -> lift.run(.3), lift));

    // controller2.button(kAButton).whileTrue(new RunCommand(() -> lift.run(-0.3), lift));

    try {
      // // left held
      stick2
          .button(kDownButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("A"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick2
          .button(kLeftButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("K"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick2
          .button(kRightButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("C"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick1
          .button(kDownButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("G"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      // stick1
      //     .button(kLeftButton)
      //     .and(stick2.button(kTrigger))
      //     .and(stick1.button(kTrigger))
      //     .onTrue(
      //         new SequentialCommandGroup(
      //         AutoBuilder.pathfindToPoseFlipped(
      //             new Branch("J").getBranchPose(), kDefaultConstraints)));

      stick1
          .button(kLeftButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("I"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));

      stick1
          .button(kRightButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("E"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));

      // // no left held
      stick2
          .button(kDownButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("B"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick2
          .button(kLeftButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("L"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick2
          .button(kRightButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("D"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick1
          .button(kDownButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("H"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick1
          .button(kLeftButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("J"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));
      stick1
          .button(kRightButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("F"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));

      stick2
          .button(kMidButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger))
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("Left Station"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));

      stick2
          .button(kMidButton)
          .and(stick2.button(kTrigger))
          .and(stick1.button(kTrigger).negate())
          .onTrue(
              new SequentialCommandGroup(
                  AutoBuilder.pathfindThenFollowPath(
                      PathPlannerPath.fromPathFile("Right Station"), kDefaultConstraints),
                  new RunCommand(() -> drive.stopWithX(), drive)));

    } catch (FileVersionException | IOException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    /*Should be breakout button; does not work */
    stick1
        .button(kBackLeftButton)
        .whileTrue(DriveCommands.joystickDrive(drive, () -> 0.0, () -> 0.0, () -> 0.0));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
