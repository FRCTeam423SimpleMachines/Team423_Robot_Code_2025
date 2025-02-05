package frc.robot.subsystems.elevator;

import static frc.robot.util.SparkUtil.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorIOSpark implements ElevatorIO {

  private final SparkFlex m_firstStageMotor;
  private final SparkMax m_secondStageMotor;
  private final RelativeEncoder m_firstStageEncoder;
  private final RelativeEncoder m_secondStageEncoder;
  private final AnalogPotentiometer m_firstStagePot;

  public ElevatorIOSpark() {
    m_firstStageMotor = new SparkFlex(ElevatorConstants.firstStageCANID, MotorType.kBrushless);
    m_secondStageMotor = new SparkMax(ElevatorConstants.secondStageCANID, MotorType.kBrushless);
    m_firstStageEncoder = m_firstStageMotor.getEncoder();
    m_secondStageEncoder = m_secondStageMotor.getEncoder();
    m_firstStagePot = new AnalogPotentiometer(1);
  }

  @Override
  public void setFirstVoltage(double voltage) {
    m_firstStageMotor.setVoltage(voltage);
  }

  @Override
  public void setSecondVoltage(double voltage) {
    m_secondStageMotor.setVoltage(voltage);
  }

  @Override
  public void updateInputs(ElevatorIOInputs inputs) {
    ifOk(
        m_firstStageMotor,
        m_firstStageEncoder::getPosition,
        (value) -> inputs.firstStagePosition = value);
    ifOk(
        m_secondStageMotor,
        m_secondStageEncoder::getPosition,
        (value) -> inputs.secondStagePosition = value);
    ifOk(
        m_firstStageMotor,
        m_firstStageEncoder::getVelocity,
        (value) -> inputs.firstStageVelocity = value);
    ifOk(
        m_secondStageMotor,
        m_secondStageEncoder::getVelocity,
        (value) -> inputs.secondStageVelocity = value);

    inputs.firstPotPos = m_firstStagePot.get();
  }
}
