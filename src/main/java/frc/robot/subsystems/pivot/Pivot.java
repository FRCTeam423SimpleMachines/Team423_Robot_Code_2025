package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Pivot extends SubsystemBase {
  private final PivotIO io;
  private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();

  public Pivot(PivotIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    if(getCurrentCommand()!=null){
      inputs.currentCommand = getCurrentCommand().getName();
    }
    else {
      inputs.currentCommand = "None";
    }
    Logger.processInputs("Pivot", inputs);
  }

  public void run(double speed) {
    io.setSpeed(speed);
  }

  public double getPosition() {
    return inputs.position;
  }
}
