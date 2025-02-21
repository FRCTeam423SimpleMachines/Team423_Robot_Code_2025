package frc.robot.subsystems.pivot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class PivotIOSim implements PivotIO {

  private final DCMotorSim m_PivotMotor;

  public PivotIOSim() {
    m_PivotMotor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 6.66e-6, 10), DCMotor.getNEO(1));
  }

  @Override
  public void setSpeed(double speed) {
    m_PivotMotor.setInputVoltage(12 * speed);
  }
}
