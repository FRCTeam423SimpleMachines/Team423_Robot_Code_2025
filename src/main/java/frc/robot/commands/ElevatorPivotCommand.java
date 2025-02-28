package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.pivot.Pivot;

public class ElevatorPivotCommand extends ParallelCommandGroup {

  public ElevatorPivotCommand(
      Elevator elevatorSubsytem, Pivot pivotSubsystem, double elavatorHeight, double pivotAngle) {
    super(
        new RunElevatorPos(elevatorSubsytem, elavatorHeight),
        new PivotToPosition(pivotSubsystem, pivotAngle));
  }
}
