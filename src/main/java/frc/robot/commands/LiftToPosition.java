package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.lift.Lift;

public class LiftToPosition extends Command {
  private final Lift lift;
  private final double liftPos;
  // private final PIDController liftController = new PIDController(1, 0, 0);
  private final ProfiledPIDController liftController =
      new ProfiledPIDController(0.1, 0.0, 0, new TrapezoidProfile.Constraints(100, 100));

  public LiftToPosition(Lift lift, double liftPos) {
    this.lift = lift;
    this.liftPos = liftPos;
    addRequirements(lift);
  }

  @Override
  public void initialize() {
    // liftController.setTolerance(0.25);
    liftController.reset(lift.getPos());
    // liftController.setGoal(liftPos);
    // liftController.setSetpoint(liftPos);
  }

  @Override
  public void execute() {
    double calc = liftController.calculate(lift.getPos(), liftPos);
    lift.run(calc);
  }

  @Override
  public void end(boolean interrupted) {}
}
