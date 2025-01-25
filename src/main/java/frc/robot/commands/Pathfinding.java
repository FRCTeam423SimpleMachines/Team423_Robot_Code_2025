package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;

public class Pathfinding extends Command {

  private Drive drive;

  public Pathfinding(Drive driveSubsytem) {
    drive = driveSubsytem;
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return super.isFinished();
  }
}
