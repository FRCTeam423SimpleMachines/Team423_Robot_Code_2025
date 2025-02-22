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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.ControlConstants;
import frc.robot.commands.AutoScoring;
import frc.robot.commands.DriveCommands;
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
                new VisionIOPhotonVision(cameraFrontName, robotToCameraFront),
                new VisionIOPhotonVision(cameraLeftName, robotToCameraLeft),
                new VisionIOPhotonVision(cameraRightName, robotToCameraRight),
                new VisionIOPhotonVision(cameraBackName, robotToCameraBack));

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
                new VisionIOPhotonVisionSim(cameraFrontName, robotToCameraFront, drive::getPose),
                new VisionIOPhotonVisionSim(cameraLeftName, robotToCameraLeft, drive::getPose),
                new VisionIOPhotonVisionSim(cameraRightName, robotToCameraRight, drive::getPose),
                new VisionIOPhotonVisionSim(cameraBackName, robotToCameraBack, drive::getPose));

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

    stationChooser = new LoggedDashboardChooser<>("Station Preference");

    stationChooser.addDefaultOption("Left", "Left");
    stationChooser.addOption("Right", "Right");

    branchChooser1 = new LoggedDashboardChooser<>("1st Branch");
    branchChooser2 = new LoggedDashboardChooser<>("2nd Branch");

    final String[] branches = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    branchChooser1.addDefaultOption("A", new Branch("A"));
    for (int i = 1; i < 12; i++) {
      branchChooser1.addOption(branches[i], new Branch(branches[i]));
      branchChooser2.addOption(branches[i], new Branch(branches[i]));
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    autoChooser.addOption(
        "AutoScoring", new AutoScoring(drive, stationChooser, branchChooser1, branchChooser2));

    autoChooser.addOption(
        "pathfinding test",
        AutoBuilder.pathfindToPoseFlipped(
            new Pose2d(2.817, 4.031, new Rotation2d(Units.degreesToRadians(-80.538))),
            kDefaultConstraints));

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
            () -> -controller1.getRawAxis(ControlConstants.kLeftYAxis),
            () -> -controller1.getRawAxis(ControlConstants.kLeftXAxis),
            () -> -controller1.getRawAxis(ControlConstants.kRightXAxis)));

    elevator.setDefaultCommand(
        new RunCommand(
            () -> elevator.runFirst(-controller2.getRawAxis(ControlConstants.kRightYAxis)),
            elevator));

    lights.setDefaultCommand(new RunCommand(() -> lights.setValue(kOff), lights));
    // Lock to 0° when A button is held
    controller1
        .button(ControlConstants.kAButton)
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> controller1.getRawAxis(ControlConstants.kLeftYAxis),
                () -> controller1.getRawAxis(ControlConstants.kLeftXAxis),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller1.button(ControlConstants.kXButton).onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    controller1
        .button(ControlConstants.kBButton)
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    controller1
        .button(ControlConstants.kAButton)
        .onTrue(
            AutoBuilder.pathfindToPoseFlipped(
                new Pose2d(2.817, 4.031, new Rotation2d(Units.degreesToRadians(-180))),
                kSlowConstraints));

    controller2.button(kRightBumper).onTrue(new RunIntakeIn(intake, lights, 0.7));

    controller2.button(kLeftBumper).onTrue(new RunCommand(() -> intake.setSpeed(-0.7), intake));

    controller1.button(kAButton).whileTrue(new RunCommand(() -> lights.setValue(kRed), lights));
    
    controller2.button(kLeftTrigger).onTrue(new RunCommand(() -> lift.run(0.5), lift));

    controller2.button(kRightTrigger).onTrue(new RunCommand(() -> lift.run(-0.5), lift)); 
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
