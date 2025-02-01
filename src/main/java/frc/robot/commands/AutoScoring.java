package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.Branch;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

public class AutoScoring extends Command {

  private LoggedDashboardChooser<Branch> chooser1;
  private Branch firstBranch;
  private final Drive drive;

  public AutoScoring(Drive driveSubsystem, LoggedDashboardChooser<Branch> firstBranchChooser) {
    chooser1 = firstBranchChooser;
    drive = driveSubsystem;
    addRequirements(drive);
  }

  @Override
  public void initialize() {
    firstBranch = chooser1.get();
    SmartDashboard.putString("First Branch Selected", firstBranch.getLetter());
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}
}
