package frc.robot.commands;

import static frc.robot.subsystems.drive.DriveConstants.kDefaultConstraints;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.Branch;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

public class AutoScoringFlex extends Command {

  private LoggedDashboardChooser<Branch>[] branchChoosers;
  private LoggedDashboardChooser<String> stationChooser;
  private Branch[] branches;
  private SequentialCommandGroup commandGroup = new SequentialCommandGroup();
  private final Drive drive;

  public AutoScoringFlex(
      Drive driveSubsystem,
      LoggedDashboardChooser<String> stationChooser,
      LoggedDashboardChooser<Branch>... branchChooser) {
    branchChoosers = branchChooser;
    this.stationChooser = stationChooser;
    drive = driveSubsystem;
    branches = new Branch[branchChoosers.length];
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    for (int i = 0; i < branchChoosers.length; i++) {
      branches[i] = branchChoosers[i].get();
      if (stationChooser.get() == "Right") {
        branches[i].setRightStationPreference();
      }
    }

    for (Branch branch : branches) {
      commandGroup.addCommands(
          AutoBuilder.pathfindToPoseFlipped(branch.getBranchPose(), kDefaultConstraints),
          AutoBuilder.pathfindToPoseFlipped(branch.getStationPose(), kDefaultConstraints));
      // TODO: Add coral placment and collection commands to group command to group
    }
  }

  @Override
  public void execute() {
    commandGroup.execute();
  }

  @Override
  public void end(boolean interrupted) {}
}
