package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.pivot.Pivot;

public class PivotToPositionAuto extends Command {
  private final ProfiledPIDController pivotController =
      new ProfiledPIDController(0.0151, 0.0, 0.0, new TrapezoidProfile.Constraints(90, 180));
  private final Pivot pivot;
  private final double position;

  public PivotToPositionAuto(Pivot pivot, double position) {
    this.pivot = pivot;
    this.position = position;
    addRequirements(pivot);
  }

  @Override
  public void initialize() {
    pivotController.enableContinuousInput(0, 360);
    pivotController.reset(pivot.getPosition());
    pivotController.setGoal(position);
    pivotController.setTolerance(1.0);
  }

  @Override
  public void execute() {
    pivot.runPow(pivotController.calculate(pivot.getPosition()));
  }

  @Override
  public boolean isFinished() {
    return pivotController.atGoal();
  }

  @Override
  public void end(boolean interrupted) {}
}
