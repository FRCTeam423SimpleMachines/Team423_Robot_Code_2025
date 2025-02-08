package frc.robot.subsystems.lift;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class LiftIOSim implements LiftIO {

  private final DCMotorSim m_liftMotor;

  public LiftIOSim() {
    m_liftMotor =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 6.66e-6, 1), DCMotor.getNEO(1));
  }

  @Override
  public void setVoltage(double voltage) {
    m_liftMotor.setInputVoltage(voltage);
  }

  @Override
  public void updateInputs(LiftIOInputs inputs) {}
}
