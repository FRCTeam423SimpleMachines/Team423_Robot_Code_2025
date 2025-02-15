package frc.robot.subsystems.intake;

import static frc.robot.util.SparkUtil.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.IntakeConstants;

public class IntakeIOSpark implements IntakeIO {
  private final SparkMax m_intakeMotor;
  private final RelativeEncoder m_intakeEncoder;
  private final DigitalInput sensorInput;

  public IntakeIOSpark() {
    m_intakeMotor = new SparkMax(IntakeConstants.intakeCANID, MotorType.kBrushless);
    m_intakeEncoder = m_intakeMotor.getEncoder();
    sensorInput = new DigitalInput(0);
  }

  @Override
  public void setVoltage(double volts) {
    m_intakeMotor.setVoltage(volts);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    ifOk(m_intakeMotor, m_intakeEncoder::getPosition, (value) -> inputs.intakePosition = value);
    ifOk(m_intakeMotor, m_intakeEncoder::getVelocity, (value) -> inputs.intakeVelocity = value);
    inputs.coral = sensorInput.get();
  }
}
