package frc.robot.subsystems.lights;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Lights extends SubsystemBase {
  private final LightsIO io;
  private final LightsIOInputsAutoLogged inputs = new LightsIOInputsAutoLogged();

  public Lights(LightsIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Lights", inputs);
  }

  public void setValue(double value) {
    io.setValue(value);
  }
}
