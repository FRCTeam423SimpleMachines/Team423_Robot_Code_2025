package frc.robot.subsystems.lift;

import org.littletonrobotics.junction.AutoLog;

public interface LiftIO {
  @AutoLog
  public static class LiftIOInputs {
    public double liftPower = 0.0;
    public double liftPosotion = 0.0;
    public String currentCommand = "None";
  }

  public default void updateInputs(LiftIOInputs inputs) {}

  public default void setVoltage(double voltage) {}

  public default void setSpeed(double speed) {}
}
