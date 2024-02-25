// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class HookSubsystem extends SubsystemBase {

  private final CANSparkMax m_hookMotor = new CANSparkMax(Constants.HookConstants.hookMotorPort, CANSparkLowLevel.MotorType.kBrushless);
  public boolean extended = false;


  //TODO waitcommand duration change
  public void retractHook(){
    m_hookMotor.set(Constants.HookConstants.hookRetractionSpeed);
    new WaitCommand(1);
    extended = false;
     m_hookMotor.set(0);
  }

  public void extendHook(){
    m_hookMotor.set(Constants.HookConstants.hookExtensionSpeed);
    extended = true;
    new WaitCommand(1);
     m_hookMotor.set(0);
  }

  public boolean getExtended(){
    return extended;
  }
  /** Creates a new ExampleSubsystem. */
  public HookSubsystem() {

  }

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
