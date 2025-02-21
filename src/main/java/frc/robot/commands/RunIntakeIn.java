package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class RunIntakeIn extends Command{
    
    private final Intake intake;
    private final double speed;

    public RunIntakeIn(Intake intakeSubsytem, Double runSpeed) {
        intake = intakeSubsytem;
        speed = Math.abs(runSpeed);

        addRequirements(intake);
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
    }
}
