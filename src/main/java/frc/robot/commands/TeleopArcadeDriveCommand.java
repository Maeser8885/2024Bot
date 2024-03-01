package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class TeleopArcadeDriveCommand extends Command {
    private DriveSubsystem driveSubsystem;
    private Supplier<Double> rotationSuppler;
    private Supplier<Double> speedSupplier;

    public TeleopArcadeDriveCommand(
            DriveSubsystem driveSubsystem,
            Supplier<Double> speedSupplier,
            Supplier<Double> rotationSuppler) {
        this.driveSubsystem = driveSubsystem;
        this.speedSupplier = speedSupplier;
        this.rotationSuppler = rotationSuppler;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        driveSubsystem.arcadeDrive(this.speedSupplier.get(), this.rotationSuppler.get());
    }
}
