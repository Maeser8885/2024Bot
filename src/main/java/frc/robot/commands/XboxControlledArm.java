package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ArmBaseMovementSubsystem;

public class XboxControlledArm extends Command {
    private ArmBaseMovementSubsystem armBaseMovementSubsystem;
    private Supplier<Double> joystickPosition;

    public XboxControlledArm(
        ArmBaseMovementSubsystem armBaseMovementSubsystem,
        Supplier<Double> joystickPosition
    ) {
        this.armBaseMovementSubsystem = armBaseMovementSubsystem;
        this.joystickPosition = joystickPosition;
        addRequirements(armBaseMovementSubsystem);
    }

    @Override
    public void execute() {
        double positionToSet = joystickPosition.get() * Constants.ArmBaseConstants.a_multiplicationControlFactor;

        if(armBaseMovementSubsystem.setpoint != positionToSet){
          armBaseMovementSubsystem.setPosition(positionToSet);
        }
    }
}
