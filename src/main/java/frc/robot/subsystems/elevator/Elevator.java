package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Elevator extends SubsystemBase {
  private final ElevatorIO io;
  private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

  public Elevator(ElevatorIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Elavator", inputs);
  }

  public Command runFisrtPercent(double percent) {
    return runEnd(() -> io.setFirstVoltage(percent * 12.0), () -> io.setFirstVoltage(0));
  }

  public Command runSecondPercent(double percent) {
    return runEnd(() -> io.setSecondVoltage(percent * 12.0), () -> io.setSecondVoltage(0));
  }
}
