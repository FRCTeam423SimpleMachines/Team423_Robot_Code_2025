package frc.robot.subsystems.lights;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LightsIOReal implements LightsIO {

  private final Spark lightController;

  public LightsIOReal() {
    lightController = new Spark(0);
  }

  @Override
  public void setValue(double colorValue) {
    lightController.set(colorValue);
  }

  @Override
  public void updateInputs(LightsIOInputs inputs) {
    inputs.color = lightController.get();
  }
}
