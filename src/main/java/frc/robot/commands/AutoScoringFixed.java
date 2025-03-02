package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.Branch;

public class AutoScoringFixed extends SequentialCommandGroup {

  private Branch firstBranch;
  private Branch secondBranch;
  private final Drive drive;
  private final Elevator elevator;
  private final Pivot pivot;
  private final Intake intake;

  public AutoScoringFixed(
      Drive driveSubsystem,
      Elevator elevatorSubsytem,
      Pivot pivotSubsystem,
      Intake intakeSubsystem) {
    firstBranch = new Branch("I");
    secondBranch = new Branch("J");
    drive = driveSubsystem;
    elevator = elevatorSubsytem;
    pivot = pivotSubsystem;
    intake = intakeSubsystem;
    addRequirements(drive);
    addCommands(
        AutoBuilder.pathfindToPoseFlipped(firstBranch.getBranchPose(), kDefaultConstraints),
        new ElevatorPivotAuto(elevator, pivot, 48.0, 340.0),
        new RunCommand(() -> intake.setSpeed(1.0), intake),
        new ElevatorPivotAuto(elevator, pivot, 31.0, 25.97),
        AutoBuilder.pathfindToPoseFlipped(firstBranch.getStationPose(), kDefaultConstraints),
        new RunIntakeIn(intake, -0.7),
        AutoBuilder.pathfindToPoseFlipped(secondBranch.getBranchPose(), kDefaultConstraints),
        new ElevatorPivotAuto(elevator, pivot, 48.0, 340.0),
        new RunCommand(() -> intake.setSpeed(1.0), intake));
  }
}
