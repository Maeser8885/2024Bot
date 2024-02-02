package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveSubsystem extends SubsystemBase{
    //initialize the motors
    PWMSparkMax rSpark = new PWMSparkMax(Constants.MotorConstants.kfrMotorPort);
    PWMSparkMax lSpark = new PWMSparkMax(Constants.MotorConstants.kflMotorPort);
    PWMSparkMax brSpark = new PWMSparkMax(Constants.MotorConstants.kbrMotorPort);
    PWMSparkMax blSpark = new PWMSparkMax(Constants.MotorConstants.kblMotorPort);
    
    public DifferentialDrive driver; 

    public DriveSubsystem(){
        rSpark.addFollower(brSpark);
        lSpark.addFollower(blSpark);
        lSpark.setInverted(true);//TODO test if this is acceptable
        driver = new DifferentialDrive(lSpark, rSpark);
        this.setDefaultCommand(getArcadeDriveCommand());
    }

    public void arcadeDrive(double xSpeed, double zRotation){
        driver.arcadeDrive(xSpeed, zRotation);
    } 

    public Command getArcadeDriveCommand(){
        return this.run(() -> arcadeDrive(RobotContainer.joystickController.getX(), -RobotContainer.joystickController.getZ()));
    }

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("FL", frSpark.get());
        // SmartDashboard.putNumber("FR", flSpark.get());
        // SmartDashboard.putNumber("BL", brSpark.get());
        // SmartDashboard.putNumber("BR", blSpark.get());
        // SmartDashboard.putNumber("Right", rightGroup.get());
        // SmartDashboard.putNumber("Left", leftGroup.get());

    }

}