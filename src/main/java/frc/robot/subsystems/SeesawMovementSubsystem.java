// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//2 Moving, 2 on springs, 1 on side wheels 1 on intake wheels, 1 on moving seesaw
package frc.robot.subsystems;

import frc.robot.RobotContainer;
import frc.robot.Constants.SeesawConstants;
import frc.robot.Settings.SeesawSettings;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SeesawMovementSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  PWMSparkMax seeSawMotor = new PWMSparkMax(SeesawConstants.seesawMotorPort);
  public SeesawMovementSubsystem() {
    
  }

  public void rotate(double direction) {
    //TODO: Make sure to figure out what motor speeds are BEFORE competition
    seeSawMotor.set(SeesawSettings.SeesawRotSpeed * direction);
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
    rotate(RobotContainer.xboxController.getRightX());    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}