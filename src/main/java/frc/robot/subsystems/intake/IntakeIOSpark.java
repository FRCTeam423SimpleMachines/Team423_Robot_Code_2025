package frc.robot.subsystems.intake;

import static frc.robot.util.SparkUtil.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import frc.robot.Constants.IntakeConstants;

public class IntakeIOSpark implements IntakeIO {
  private final SparkMax m_intakeMotor;
  private final RelativeEncoder m_intakeEncoder;

  public IntakeIOSpark() {
    m_intakeMotor = new SparkMax(IntakeConstants.intakeCANID, null);
    m_intakeEncoder = m_intakeMotor.getEncoder();
  }

  @Override
  public void setVoltage(double volts) {
    m_intakeMotor.setVoltage(volts);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    ifOk(m_intakeMotor, m_intakeEncoder::getPosition, (value) -> inputs.intakePosition = value);
    ifOk(m_intakeMotor, m_intakeEncoder::getVelocity, (value) -> inputs.intakeVelocity = value);
  }
}
