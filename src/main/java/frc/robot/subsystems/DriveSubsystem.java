package frc.robot.subsystems;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveSubsystem extends SubsystemBase{
    //initialize the motors
    Spark rSpark = new Spark(Constants.MotorConstants.kfrMotorPort);
    Spark lSpark = new Spark(Constants.MotorConstants.kflMotorPort);
    Spark brSpark = new Spark(Constants.MotorConstants.kbrMotorPort);
    Spark blSpark = new Spark(Constants.MotorConstants.kblMotorPort);
    
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
        //TODO potentially edit these values
        return this.run(() -> arcadeDrive(RobotContainer.joystickController.getX(), -RobotContainer.joystickController.getTwist()*0.8));
    }

    @Override
    public void periodic() {
        

    }

}