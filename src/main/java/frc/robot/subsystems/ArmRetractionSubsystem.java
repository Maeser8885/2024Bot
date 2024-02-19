// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.*;

public class ArmRetractionSubsystem extends SubsystemBase {
  private final CANSparkMax m_winchMotor = new CANSparkMax(Constants.RetractionConstants.winchMotorPort, CANSparkLowLevel.MotorType.kBrushless);
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
  public void extendWinch(){
    m_winchMotor.set(Constants.RetractionConstants.winchSpeed);
  }

  public void retractWinch(){
    m_winchMotor.set(-Constants.RetractionConstants.winchSpeed);//TODO check if gud
  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
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
