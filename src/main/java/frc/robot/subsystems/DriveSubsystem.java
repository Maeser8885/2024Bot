package frc.robot.subsystems;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
      rSpark.setInverted(true);//tested, this is acceptable, if something is wrong check ports
        
        
        driver = new DifferentialDrive(lSpark, rSpark);
    }

    public void arcadeDrive(double xSpeed, double zRotation){
        driver.arcadeDrive(xSpeed, zRotation);
    } 
      

    @Override
    public void periodic() {
        SmartDashboard.putNumber("RSPARK", rSpark.get());
        SmartDashboard.putNumber("LSPARK", lSpark.get());
        SmartDashboard.putNumber("BRSPARK", brSpark.get());
        SmartDashboard.putNumber("BLSPARK", blSpark.get());

    }

    
  

  private double adjustThrottle(double throttle) {
    return -throttle/2 +1;
}

// public double getThrottledY(){
//   return RobotContainer.joystickController.getY() * adjustThrottle(RobotContainer.joystickController.getThrottle());
// }

// public double getThrottledTwist(){
//   return RobotContainer.joystickController.getTwist() * adjustThrottle(RobotContainer.joystickController.getThrottle());
// }

}