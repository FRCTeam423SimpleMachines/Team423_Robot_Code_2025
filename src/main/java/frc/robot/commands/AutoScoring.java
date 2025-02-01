package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.Branch;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

public class AutoScoring extends Command {

  private LoggedDashboardChooser<Branch> branchChooser1;
  private LoggedDashboardChooser<Branch> branchChooser2;
  private LoggedDashboardChooser<String> stationChooser;
  private Branch firstBranch;
  private Branch secondBranch;
  private final Drive drive;

  public AutoScoring(
      Drive driveSubsystem,
      LoggedDashboardChooser<String> stationChooser,
      LoggedDashboardChooser<Branch> firstBranchChooser,
      LoggedDashboardChooser<Branch> secondBranchChooser) {
    branchChooser1 = firstBranchChooser;
    branchChooser2 = secondBranchChooser;
    this.stationChooser = stationChooser;
    drive = driveSubsystem;
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    firstBranch = branchChooser1.get();
    secondBranch = branchChooser2.get();
    if (stationChooser.get() == "Right") {}
  }

  @Override
  public void execute() {
    try {
      new SequentialCommandGroup(
              AutoBuilder.followPath(PathPlannerPath.fromPathFile(firstBranch.getStartPath())),
              AutoBuilder.followPath(PathPlannerPath.fromPathFile(firstBranch.getStartPath())),
              AutoBuilder.pathfindToPose(secondBranch.getFacePose(), kDefaultConstraints))
          .execute();
    } catch (Exception e) {
      end(true);
    }
  }

  @Override
  public void end(boolean interrupted) {}
}
