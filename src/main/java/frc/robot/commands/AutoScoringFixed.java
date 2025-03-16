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
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class AutoScoringFixed extends SequentialCommandGroup {

  public AutoScoringFixed(
      Drive drive,
      Elevator elevator,
      Pivot pivot,
      Intake intake,
      String firstBranch,
      String secondBranch,
      String station) {

    addRequirements(drive, elevator, pivot, intake);

    try {
      addCommands(
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile(firstBranch), kDefaultConstraints),
          // new PivotToPositionAuto(pivot, 330),
          // new RunElevatorPosAuto(elevator, 74.0),
          new ElevatorPivotAuto(elevator, pivot, 70.0, 335), // L4
          new RunIntakeOut(intake, 1.0),
          // new PivotToPositionAuto(pivot, 26.0),
          // new RunElevatorPosAuto(elevator, 19.0),
          new ElevatorPivotAuto(elevator, pivot, 28.0, 26), // Station
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile(station), kDefaultConstraints),
          new RunIntakeIn(intake, -0.7),
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile(secondBranch), kDefaultConstraints),
          // new PivotToPositionAuto(pivot, 330),
          // new RunElevatorPosAuto(elevator, 74.0),
          new ElevatorPivotAuto(elevator, pivot, 70.0, 335), // L4
          new RunIntakeOut(intake, 1.0));
    } catch (FileVersionException | IOException | ParseException e) {
      end(true);
    }
  }
}
