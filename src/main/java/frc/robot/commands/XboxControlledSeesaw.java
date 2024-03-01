package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SeesawMovementSubsystem;

public class XboxControlledSeesaw extends Command{
    private SeesawMovementSubsystem seesawMovementSubsystem;
    private Supplier<Double> joystickPosition;
    
    public XboxControlledSeesaw(SeesawMovementSubsystem seesawMovementSubsystem,
    Supplier<Double> joystickPosition){
        this.seesawMovementSubsystem = seesawMovementSubsystem;
        this.joystickPosition = joystickPosition;
        addRequirements(seesawMovementSubsystem);
    }

    @Override
    public void execute(){
        double positionToSet = joystickPosition.get() * Constants.SeesawConstants.kMultiplicationControlFactor;

        if(seesawMovementSubsystem.setpoint != positionToSet){
          seesawMovementSubsystem.rotateToAngle(positionToSet);
        }
    }
}
