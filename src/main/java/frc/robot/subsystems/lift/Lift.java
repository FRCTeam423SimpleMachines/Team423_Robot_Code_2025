package frc.robot.subsystems.lift;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Lift extends SubsystemBase {
  private final LiftIO io;
  private final LiftIOInputsAutoLogged inputs = new LiftIOInputsAutoLogged();

  public Lift(LiftIO io) {
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
    Logger.processInputs("Lift", inputs);
  }

  public Command runPercent(double percent) {
    return runEnd(() -> io.setVoltage(percent * 12.0), () -> io.setVoltage(0));
  }

  public void run(double speed) {
    io.setSpeed(speed);
  }
}
