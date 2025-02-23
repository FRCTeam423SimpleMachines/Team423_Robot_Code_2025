package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    public boolean coral = false;
    public String currentCommand = "None";
  }

  public default void updateInputs(IntakeIOInputs inputs) {}

  public default void setVoltage(double volts) {}

  public default void setSpeed(double speed) {}
}
