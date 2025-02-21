package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class RunElevatorPos extends Command{
    private final Elevator elevator;
    private final double firstPos;
    private final double secondPos;
    private final PIDController firstStageController = new PIDController(0, 0, 0);
    private final PIDController secondStageController = new PIDController(0, 0, 0);

    public RunElevatorPos(Elevator elevator, double firstPos, double secondPos) {
        this.elevator = elevator;
        this.firstPos = firstPos; 
        this.secondPos = secondPos;
    }

    @Override
    public void initialize() {
        firstStageController.setSetpoint(firstPos);
        secondStageController.setSetpoint(secondPos);
    }

    @Override
    public void execute() {
        elevator.runFirst(firstStageController.calculate(elevator.getFirstPos()));
        elevator.runSecond(secondStageController.calculate(elevator.getSecondPos()));
    }
    
    @Override
    public boolean isFinished() {
        return firstStageController.atSetpoint() && secondStageController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {}



}
