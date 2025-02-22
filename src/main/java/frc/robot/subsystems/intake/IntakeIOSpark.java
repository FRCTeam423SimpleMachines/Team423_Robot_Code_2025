package frc.robot.subsystems.intake;

import static frc.robot.Constants.IntakeConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeIOSpark implements IntakeIO {
  private final TalonSRX m_intakeMotor;
  private final DigitalInput sensorInput;

  public IntakeIOSpark() {
    m_intakeMotor = new TalonSRX(kIntakeCANID);
    sensorInput = new DigitalInput(0);
  }

  @Override
  public void setSpeed(double speed) {
    m_intakeMotor.set(ControlMode.Position, speed);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.coral = sensorInput.get();
  }
}
