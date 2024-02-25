package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RotateAngleCommand extends Command {
    private final DriveSubsystem m_subsystem;
    private double m_angle;

    public RotateAngleCommand(double angle, DriveSubsystem subsystem) {
        m_angle = angle;
        m_subsystem = subsystem;

        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_subsystem.arcadeDrive(Constants.AutoConstants.turnSpeed, m_angle);
    }

    
    



}
