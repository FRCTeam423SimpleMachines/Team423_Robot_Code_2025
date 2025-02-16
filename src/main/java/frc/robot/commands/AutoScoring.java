package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
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
  private boolean rightPrefence = false;
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
    if (stationChooser.get() == "Right") {
      firstBranch.setRightStationPreference();
      secondBranch.setRightStationPreference();
    }
  }

  @Override
  public void execute() {
    new SequentialCommandGroup(
      AutoBuilder.pathfindToPoseFlipped(firstBranch.getBranchPose(), kDefaultConstraints),
      AutoBuilder.pathfindToPoseFlipped(firstBranch.getStationPose(), kDefaultConstraints),
      AutoBuilder.pathfindToPoseFlipped(firstBranch.getBranchPose(), kDefaultConstraints)
    ).execute();
  }

  @Override
  public void end(boolean interrupted) {}
}
