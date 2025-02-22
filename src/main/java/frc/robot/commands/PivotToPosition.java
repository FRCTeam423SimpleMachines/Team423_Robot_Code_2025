package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.pivot.Pivot;

public class PivotToPosition extends Command {
  private final PIDController pivotController = new PIDController(0, 0, 0);
  private final Pivot pivot;
  private final double position;

  public PivotToPosition(Pivot pivot, double position) {
    this.pivot = pivot;
    this.position = position;
  }

  @Override
  public void initialize() {
    pivotController.setSetpoint(position);
  }

  @Override
  public void execute() {
    pivot.run(pivotController.calculate(pivot.getPosition()));
  }

  @Override
  public boolean isFinished() {
    return pivotController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {}
}
