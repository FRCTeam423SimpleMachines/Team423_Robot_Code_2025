package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.pivot.Pivot;

public class ElevatorPivotAuto extends ParallelCommandGroup {

  public ElevatorPivotAuto(
      Elevator elevatorSubsytem, Pivot pivotSubsystem, double elavatorHeight, double pivotAngle) {
    super(
        new RunElevatorPosAuto(elevatorSubsytem, elavatorHeight),
        new PivotToPositionAuto(pivotSubsystem, pivotAngle));
  }
}
