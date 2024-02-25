// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import com.revrobotics.*;

public class ArmRetractionSubsystem extends SubsystemBase {
  private final CANSparkMax m_winchMotor = new CANSparkMax(Constants.RetractionConstants.winchMotorPort, CANSparkLowLevel.MotorType.kBrushless);
  
  boolean extended = false;
  public ArmRetractionSubsystem() {}
    
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  //TODO change waitcommands
  public void extendWinch(){
    m_winchMotor.set(Constants.RetractionConstants.winchSpeed);
    extended = true;
    new WaitCommand(1);
     m_winchMotor.set(0);
  }

  public void retractWinch(){
    m_winchMotor.set(-Constants.RetractionConstants.winchSpeed); 
    extended = false;
    new WaitCommand(1);
     m_winchMotor.set(0);
  }//TODO check if gud
  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */

  public boolean getExtended(){return extended;}
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
