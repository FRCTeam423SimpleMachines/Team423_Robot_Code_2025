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

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;
import static frc.robot.subsystems.vision.VisionConstants.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.ControlConstants;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSpark;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
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

  // Controller
  private final CommandJoystick controller = new CommandJoystick(0);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

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
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // try {
    //   Command toIJ =
    //       AutoBuilder.pathfindThenFollowPath(
    //           PathPlannerPath.fromPathFile("Reef IJ"), kDefaultConstraints);
    //   Command toLeftStation =
    //       AutoBuilder.pathfindThenFollowPath(
    //           PathPlannerPath.fromPathFile("Left Station"), kDefaultConstraints);
    //   Command toAB =
    //       AutoBuilder.pathfindThenFollowPath(
    //           PathPlannerPath.fromPathFile("Reef AB"), kDefaultConstraints);
    //   // autoChooser.addOption("Multi Coral Test", new SequentialCommandGroup(toIJ));
    //   autoChooser.addOption("IJ", toIJ);
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }

    autoChooser.addOption(
        "pathfinding test",
        AutoBuilder.pathfindToPoseFlipped(
            new Pose2d(2.817, 4.031, new Rotation2d(Units.degreesToRadians(-80.538))),
            kDefaultConstraints));

    try {
      autoChooser.addOption(
          "pathfind to path test",
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile("Test Path"), kDefaultConstraints));
    } catch (Exception e) {
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
            () -> -controller.getRawAxis(ControlConstants.kLeftYAxis),
            () -> -controller.getRawAxis(ControlConstants.kLeftXAxis),
            () -> -controller.getRawAxis(ControlConstants.kRightXAxis)));

    // Lock to 0° when A button is held
    controller
        .button(ControlConstants.kAButton)
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> controller.getRawAxis(ControlConstants.kLeftYAxis),
                () -> controller.getRawAxis(ControlConstants.kLeftXAxis),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller.button(ControlConstants.kXButton).onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    controller
        .button(ControlConstants.kBButton)
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));
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
