package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs {
    public double firstStagePosition = 0.0;
    public double secondStagePosition = 0.0;
    public double firstStageVelocity = 0.0;
    public double secondStageVelocity = 0.0;
    public double firstPotPos = 0.0;
    public double secondPotPos = 0.0;
    public double firstVoltage = 0.0;
    public double firstCurrunet = 0.0;
    public double secondVoltage = 0.0;
    public double secondCurrunet = 0.0;
    public String currentCommand = "None";
  }

  public default void updateInputs(ElevatorIOInputs inputs) {}

  public default void setFirstSpeed(double speed) {}

  public default void setSecondSpeed(double speed) {}

  public default double getFirstPosition() {
    return 0.0;
  }

  public default double getSecondPosition() {
    return 0.0;
  }

  public default double getFirstVelocity() {
    return 0.0;
  }

  public default double getSecondVelocity() {
    return 0.0;
  }
}
