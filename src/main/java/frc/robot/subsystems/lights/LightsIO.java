package frc.robot.subsystems.lights;

import org.littletonrobotics.junction.AutoLog;

public interface LightsIO {
  @AutoLog
  public static class LightsIOInputs {
    public double color = 0.0;
    public String currentCommand = "None";
  }

  public default void updateInputs(LightsIOInputs inputs) {}

  public default void setValue(double colorValue) {}
}
