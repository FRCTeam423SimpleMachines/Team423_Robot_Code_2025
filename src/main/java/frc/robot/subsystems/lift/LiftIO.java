package frc.robot.subsystems.lift;

import org.littletonrobotics.junction.AutoLog;

public interface LiftIO {
  @AutoLog
  public static class LiftIOInputs {
    public double liftPower;
    public double liftPosotion;
  }

  public default void updateInputs(LiftIOInputs inputs) {}

  public default void setVoltage(double voltage) {}

  public default void setSpeed(double speed) {}
}
