package frc.robot.subsystems.elevator;

import static frc.robot.Constants.ElevatorConstants.*;
import static frc.robot.util.SparkUtil.ifOk;

import au.grapplerobotics.LaserCan;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorIOSpark implements ElevatorIO {

  private final SparkFlex m_firstStageMotor;
  private final SparkMax m_secondStageMotor;
  private final RelativeEncoder m_firstStageEncoder;
  private final RelativeEncoder m_secondStageEncoder;
  private final LaserCan m_firstLaser;
  private final LaserCan m_secondLaser;
  // private final AnalogPotentiometer m_firstStagePot;
  // private final AnalogPotentiometer m_secondStagePot;

  public ElevatorIOSpark() {
    m_firstStageMotor = new SparkFlex(ElevatorConstants.kFirstStageCANID, MotorType.kBrushless);
    m_secondStageMotor = new SparkMax(ElevatorConstants.kSecondStageCANID, MotorType.kBrushless);
    m_firstStageEncoder = m_firstStageMotor.getEncoder();
    m_secondStageEncoder = m_secondStageMotor.getEncoder();
    m_firstLaser = new LaserCan(kFirstLaserCANID);
    m_secondLaser = new LaserCan(kSecondLaserCANID);
    // m_firstStagePot =
    //     new AnalogPotentiometer(kFirstPotChannel, firstPotToInchesFactor, firstPotOffset);
    // m_secondStagePot =
    //     new AnalogPotentiometer(kSecondPotChanel, secondPotToInchesFactor, secondPotOffset);
  }

  @Override
  public void setFirstSpeed(double speed) {
    m_firstStageMotor.set(speed);
  }

  @Override
  public void setSecondSpeed(double speed) {
    m_secondStageMotor.set(speed);
  }

  @Override
  public double getFirstPosition() {
    return Units.metersToInches(0.001 * m_firstLaser.getMeasurement().distance_mm)
        - firstLaserOffset;
  }

  @Override
  public double getSecondPosition() {
    return Units.metersToInches(0.001 * m_secondLaser.getMeasurement().distance_mm)
        - secondLaserOffset;
  }

  @Override
  public double getFirstVelocity() {
    return m_firstStageEncoder.getVelocity();
  }

  @Override
  public double getSecondVelocity() {
    return m_secondStageEncoder.getVelocity();
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
        (value) -> inputs.firstStageRPM = value);
    ifOk(
        m_secondStageMotor,
        m_secondStageEncoder::getVelocity,
        (value) -> inputs.secondStageRPM = value);
    ifOk(
        m_firstStageMotor,
        m_firstStageMotor::getBusVoltage,
        (value) -> inputs.firstVoltage = value);
    ifOk(
        m_firstStageMotor,
        m_firstStageMotor::getOutputCurrent,
        (value) -> inputs.firstCurrunet = value);
    ifOk(
        m_secondStageMotor,
        m_secondStageMotor::getBusVoltage,
        (value) -> inputs.secondVoltage = value);
    ifOk(
        m_secondStageMotor,
        m_secondStageMotor::getOutputCurrent,
        (value) -> inputs.secondCurrunet = value);

    inputs.firstPotPos = getFirstPosition();
    inputs.secondPotPos = getSecondPosition();
  }
}
