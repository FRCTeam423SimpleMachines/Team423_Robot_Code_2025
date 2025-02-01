package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.Branch;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;

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
    
  }

  @Override
  public void end(boolean interrupted) {}
}
