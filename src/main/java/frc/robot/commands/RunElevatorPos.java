package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class RunElevatorPos extends Command {
  private final Elevator elevator;
  private final double elevatorPos;
  // private final PIDController elevatorController = new PIDController(1, 0, 0);
  private final ProfiledPIDController elevatorController =
      new ProfiledPIDController(0.05, 0.001, 0, new TrapezoidProfile.Constraints(10, 1));

  public RunElevatorPos(Elevator elevator, double elevatorPos) {
    this.elevator = elevator;
    this.elevatorPos = elevatorPos;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    // elevatorController.setGoal(elevatorPos);
    // elevatorController.setSetpoint(elevatorPos);
  }

  @Override
  public void execute() {
    double calc = elevatorController.calculate(elevator.getTotalPos(), elevatorPos);
    elevator.runFirst(calc);
    elevator.runSecond(calc);
  }

  @Override
  public void end(boolean interrupted) {}
}
