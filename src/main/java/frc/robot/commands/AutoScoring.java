package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoScoring extends Command {

    boolean[] ignoredFaces = new boolean[6];
    GenericEntry[] ignored;

    public AutoScoring(GenericEntry[] faceToggles) {
        ignored = faceToggles;
    }

    @Override
    public void initialize() {
        ignoredFaces[0]=ignored[0].getBoolean(true);
        ignoredFaces[1]=ignored[1].getBoolean(true);
        ignoredFaces[2]=ignored[2].getBoolean(true);
        ignoredFaces[3]=ignored[3].getBoolean(true);
        ignoredFaces[4]=ignored[4].getBoolean(true);
        ignoredFaces[5]=ignored[5].getBoolean(true);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
