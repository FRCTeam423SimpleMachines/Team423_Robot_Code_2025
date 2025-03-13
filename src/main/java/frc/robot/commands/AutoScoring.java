package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.Branch;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

public class AutoScoring extends Command {

  private LoggedDashboardChooser<Branch> branchChooser1;
  private LoggedDashboardChooser<Branch> branchChooser2;
  private LoggedDashboardChooser<String> stationChooser;
  private Branch firstBranch;
  private Branch secondBranch;
  private final Drive drive;
  private final Elevator elevator;
  private final Pivot pivot;
  private final Intake intake;

  public AutoScoring(
      Drive driveSubsystem,
      Elevator elevatorSubsytem,
      Pivot pivotSubsystem,
      Intake intakeSubsystem,
      LoggedDashboardChooser<String> stationChooser,
      LoggedDashboardChooser<Branch> firstBranchChooser,
      LoggedDashboardChooser<Branch> secondBranchChooser) {
    branchChooser1 = firstBranchChooser;
    branchChooser2 = secondBranchChooser;
    this.stationChooser = stationChooser;
    drive = driveSubsystem;
    elevator = elevatorSubsytem;
    pivot = pivotSubsystem;
    intake = intakeSubsystem;
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    firstBranch = branchChooser1.get();
    secondBranch = branchChooser2.get();
    if (stationChooser.get() == "Right") {
      firstBranch.setRightStationPreference();
      secondBranch.setRightStationPreference();
    }
  }

  @Override
  public void execute() {

    new SequentialCommandGroup(
            AutoBuilder.pathfindToPoseFlipped(firstBranch.getBranchPose(), kDefaultConstraints),
            new ElevatorPivotAuto(elevator, pivot, 48.0, 340.0),
            new RunCommand(() -> intake.setSpeed(1.0), intake),
            new ElevatorPivotAuto(elevator, pivot, 31.0, 25.97),
            AutoBuilder.pathfindToPoseFlipped(firstBranch.getStationPose(), kDefaultConstraints),
            new RunIntakeIn(intake, -0.7),
            AutoBuilder.pathfindToPoseFlipped(secondBranch.getBranchPose(), kDefaultConstraints),
            new ElevatorPivotAuto(elevator, pivot, 48.0, 340.0),
            new RunCommand(() -> intake.setSpeed(1.0), intake))
        .execute();
  }

  @Override
  public void end(boolean interrupted) {}
}
