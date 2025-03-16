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

public class AutoScoringSingle extends SequentialCommandGroup {

  public AutoScoringSingle(
      Drive drive, Elevator elevator, Pivot pivot, Intake intake, String firstBranch) {

    addRequirements(drive, elevator, pivot, intake);

    try {
      addCommands(
          AutoBuilder.pathfindThenFollowPath(
              PathPlannerPath.fromPathFile(firstBranch), kDefaultConstraints),
          new ElevatorPivotAuto(elevator, pivot, 70, 330),
          new RunIntakeOut(intake, 1.0),
          new PivotToPositionAuto(pivot, 26.0),
          new RunElevatorPosAuto(elevator, 19.0));
    } catch (FileVersionException | IOException | ParseException e) {
      end(true);
    }
  }
}
