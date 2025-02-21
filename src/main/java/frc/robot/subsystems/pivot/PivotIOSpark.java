package frc.robot.subsystems.pivot;

import static frc.robot.Constants.PivotConstants.*;
import static frc.robot.util.SparkUtil.*;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class PivotIOSpark implements PivotIO {

  private final SparkMax m_pivotMotor;
  private final AbsoluteEncoder m_pivotEconder;

  public PivotIOSpark() {
    m_pivotMotor = new SparkMax(kPivotCANID, MotorType.kBrushless);
    m_pivotEconder = m_pivotMotor.getAbsoluteEncoder();
  }

  @Override
  public void setSpeed(double speed) {
    m_pivotMotor.set(speed);
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    ifOk(m_pivotMotor, m_pivotEconder::getPosition, (value) -> inputs.position = value);
    ifOk(m_pivotMotor, m_pivotMotor::getOutputCurrent, (value) -> inputs.current = value);
    ifOk(m_pivotMotor, m_pivotMotor::getBusVoltage, (value) -> inputs.voltage = value);
  }
}
