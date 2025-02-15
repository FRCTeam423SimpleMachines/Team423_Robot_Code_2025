package frc.robot.subsystems.elevator;

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

  public void runFirst(double speed) {
    io.setFirstSpeed(speed);
  }

  public void runSecond(double speed) {
    io.setSecondSpeed(speed);
  }
  // public Command runFirst(double speed) {
  //   return runEnd(() -> io.setFirstSpeed(percent * 12.0), () -> io.setFirstVoltage(0));
  // }

  // public Command runSecond(double speed) {
  //   return runEnd(() -> io.setSecondSpeed(percent * 12.0), () -> io.setSecondVoltage(0));
  // }
}
