package frc.robot.commands;

import static frc.robot.Constants.LightConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.lights.Lights;

public class RunIntakeIn extends Command {

  private final Intake intake;
  private final Lights lights;
  private final double speed;

  public RunIntakeIn(Intake intakeSubsytem, Lights lightSubsystem, Double runSpeed) {
    intake = intakeSubsytem;
    lights = lightSubsystem;
    speed = Math.abs(runSpeed);

    addRequirements(intake);
  }

  @Override
  public void initialize() {
    lights.setValue(kRed);
  }

  @Override
  public void execute() {
    intake.setSpeed(speed);
  }

  @Override
  public boolean isFinished() {
    return intake.isCoral();
  }

  @Override
  public void end(boolean interrupted) {
    intake.setSpeed(0.0);
    lights.setValue(kGreen);
  }
}
