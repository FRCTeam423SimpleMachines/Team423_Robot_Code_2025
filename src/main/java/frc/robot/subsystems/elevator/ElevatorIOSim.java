package frc.robot.subsystems.elevator;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ElevatorIOSim implements ElevatorIO {

  private final DCMotorSim m_firstStageMotor;
  private final DCMotorSim m_secondStageMotor;

  public ElevatorIOSim() {
    m_firstStageMotor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 6.66e-6, 1),
            DCMotor.getNeoVortex(1));
    m_secondStageMotor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 6.66e-6, 1), DCMotor.getNEO(1));
  }

  // @Override
  // public void setFirstVoltage(double voltage) {
  //   m_firstStageMotor.setInputVoltage(voltage);
  // }

  // @Override
  // public void setSecondVoltage(double voltage) {
  //   m_secondStageMotor.setInputVoltage(voltage);
  // }

  @Override
  public void updateInputs(ElevatorIOInputs inputs) {
    m_firstStageMotor.update(0.02);
    m_secondStageMotor.update(0.02);

    inputs.firstStagePosition = m_firstStageMotor.getAngularPositionRotations();
    inputs.secondStagePosition = m_secondStageMotor.getAngularPositionRotations();
    inputs.firstStageVelocity = m_firstStageMotor.getAngularVelocityRPM();
    inputs.secondStageVelocity = m_secondStageMotor.getAngularVelocityRPM();
  }
}
