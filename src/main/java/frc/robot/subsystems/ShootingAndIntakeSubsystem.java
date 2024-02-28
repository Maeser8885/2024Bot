// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootingAndIntakeSubsystem extends SubsystemBase {
  
  CANSparkMax m_shootingandIntakeMotor = new CANSparkMax(Constants.shootingAndIntakeConstants.kshootMotorPort, CANSparkLowLevel.MotorType.kBrushless);
  CANSparkMax m_intakeMotor = new CANSparkMax(Constants.shootingAndIntakeConstants.intakeMotor, CANSparkLowLevel.MotorType.kBrushless);

  //Variables for stopping shooting or intake
  // boolean isShooting = false;
  Timer timer = new Timer();
  boolean resetMotorThisFrame = true;

  public ShootingAndIntakeSubsystem() {timer.start();}

  public void shootNote(){
    m_shootingandIntakeMotor.set(Constants.shootingAndIntakeConstants.shootspeed);

    //Resetting timer that will stop the motors after an amount of time
    // timer.reset();
    // isShooting = true;

    // shootingandIntakeMotor.stopMotor();
  }
  public void stopShooting(){
    m_shootingandIntakeMotor.set(0);
  }

  public void intakeNote(){
    m_shootingandIntakeMotor.set(Constants.shootingAndIntakeConstants.intakeSpeed);
    m_intakeMotor.set(Constants.shootingAndIntakeConstants.intakeSpeed);

    //Resetting timer that will stop the motors after an amount of time 
    // timer.reset();
    // isShooting = false;
    
    // shootingandIntakeMotor.stopMotor();
    // intakeMotor.stopMotor();
  }
  public void stopIntake(){
    m_shootingandIntakeMotor.set(0);
    m_intakeMotor.set(0);
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
    /* 
    if (isShooting && timer.get() > Constants.shootingAndIntakeConstants.secondsToShoot){
      m_shootingandIntakeMotor.set(0);
    }
    if (!isShooting && timer.get() > Constants.shootingAndIntakeConstants.secondsToIntake){
      m_shootingandIntakeMotor.set(0);
      m_intakeMotor.set(0);
    }
    */

    SmartDashboard.putNumber("Shooting/Intake motor value", m_shootingandIntakeMotor.get());
    SmartDashboard.putNumber("Intake motor value", m_intakeMotor.get());
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
