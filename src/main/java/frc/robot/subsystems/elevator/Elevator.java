package frc.robot.subsystems.elevator;

import static frc.robot.Constants.ElevatorConstants.*;

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
    if (getCurrentCommand() != null) {
      inputs.currentCommand = getCurrentCommand().getName();
    } else {
      inputs.currentCommand = "None";
    }
    Logger.processInputs("Elavator", inputs);
  }

  public void runFirst(double speed) {
    if ((getFirstPos() >= firstUpperBound) && (speed > 0)) {
      speed = 0;
    }
    if ((getFirstPos() <= firstLowerBound) && (speed < 0)) {
      speed = 0;
    }
    io.setFirstSpeed(speed);
  }

  public void runSecond(double speed) {
    if ((getSecondPos() >= secondUpperBound) && (speed > 0)) {
      speed = 0;
    }
    if ((getSecondPos() <= secondLowerBound) && (speed < 0)) {
      speed = 0;
    }
    io.setSecondSpeed(speed);
  }

  public double getFirstPos() {
    return io.getFirstPosition();
  }

  public double getSecondPos() {
    return io.getSecondPosition();
  }

  public double getTotalPos() {
    return (getFirstPos() + getSecondPos() + elevatorOffset);
  }

  // public Command runFirst(double speed) {
  //   return runEnd(() -> io.setFirstSpeed(percent * 12.0), () -> io.setFirstVoltage(0));
  // }

  // public Command runSecond(double speed) {
  //   return runEnd(() -> io.setSecondSpeed(percent * 12.0), () -> io.setSecondVoltage(0));
  // }
}
