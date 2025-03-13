package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.util.Branch;
import java.io.IOException;
import org.json.simple.parser.ParseException;

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

    try {
      addCommands(
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile("I"), kDefaultConstraints),
          new PivotToPositionAuto(pivot, 330),
          new RunElevatorPosAuto(elevator, 74.0),
          // new ElevatorPivotAuto(elevator, pivot, 78.0, 330), // L4
          new RunIntakeOut(intake, 1.0),
          new ElevatorPivotAuto(elevator, pivot, 28.0, 26), // Station
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile("Left Station"), kDefaultConstraints),
          new RunIntakeIn(intake, -0.7),
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile("J"), kDefaultConstraints),
          new ElevatorPivotAuto(elevator, pivot, 78.0, 330), // L4
          new RunIntakeOut(intake, 1.0));
    } catch (FileVersionException | IOException | ParseException e) {
      end(true);
    }
  }
}
