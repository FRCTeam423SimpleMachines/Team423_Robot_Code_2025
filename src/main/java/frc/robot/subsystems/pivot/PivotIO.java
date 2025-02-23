package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
  @AutoLog
  public static class PivotIOInputs {
    public double position = 0.0;
    public double voltage = 0.0;
    public double current = 0.0;
    public String currentCommand = "None";
  }

  public default void updateInputs(PivotIOInputs inputs) {}

  public default void setSpeed(double speed) {}
}
