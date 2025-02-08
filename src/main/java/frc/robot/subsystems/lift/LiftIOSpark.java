package frc.robot.subsystems.lift;

import static frc.robot.Constants.LiftConstants.*;
import static frc.robot.util.SparkUtil.*;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

public class LiftIOSpark implements LiftIO {

  private final SparkMax m_liftMotor;
  private final RelativeEncoder m_liftEconder;
  private final AbsoluteEncoder m_liftAbsoluteEncoder;

  public LiftIOSpark() {
    m_liftMotor = new SparkMax(kLiftCANID, MotorType.kBrushless);
    m_liftEconder = m_liftMotor.getEncoder();
    m_liftAbsoluteEncoder = m_liftMotor.getAbsoluteEncoder();
  }

  @Override
  public void setVoltage(double voltage) {
    m_liftMotor.setVoltage(voltage);
  }

  @Override
  public void setSpeed(double speed) {
    m_liftMotor.set(speed);
  }

  @Override
  public void updateInputs(LiftIOInputs inputs) {
    ifOk(m_liftMotor, m_liftMotor::get, (value) -> inputs.liftPower = value);
    ifOk(m_liftMotor, m_liftAbsoluteEncoder::getPosition, (value) -> inputs.liftPosotion = value);
  }
}
