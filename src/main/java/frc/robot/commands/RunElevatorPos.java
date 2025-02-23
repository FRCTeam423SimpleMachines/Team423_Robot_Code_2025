package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class RunElevatorPos extends Command {
  private final Elevator elevator;
  private final double elevatorPos;
  private final PIDController elevatorController = new PIDController(0, 0, 0);

  public RunElevatorPos(Elevator elevator, double elevatorPos) {
    this.elevator = elevator;
    this.elevatorPos = elevatorPos;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    elevatorController.setSetpoint(elevatorPos);
  }

  @Override
  public void execute() {
    double calc = elevatorController.calculate(elevator.getTotalPos());
    elevator.runFirst(calc);
    elevator.runSecond(calc);
  }

  @Override
  public boolean isFinished() {
    return elevatorController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {}
}
