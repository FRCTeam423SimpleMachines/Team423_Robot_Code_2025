package frc.robot.subsystems.intake;

import static frc.robot.util.SparkUtil.*;
import static frc.robot.Constants.IntakeConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;


public class IntakeIOSpark implements IntakeIO {
  private final PWMTalonSRX m_intakeMotor;
  private final DigitalInput sensorInput;

  public IntakeIOSpark() {
    m_intakeMotor = new PWMTalonSRX(kIntakeCANID);
    sensorInput = new DigitalInput(0);
  }

  @Override
  public void setVoltage(double volts) {
    m_intakeMotor.setVoltage(volts);
  }

  @Override
  public void setSpeed(double speed) {
      m_intakeMotor.set(speed);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.coral = sensorInput.get();
  }
}
