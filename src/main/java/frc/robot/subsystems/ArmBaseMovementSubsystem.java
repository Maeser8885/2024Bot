// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmBaseMovementSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  CANSparkMax leftBaseMotor = new CANSparkMax(Constants.ArmBaseConstants.baseLeftMotorPort, CANSparkLowLevel.MotorType.kBrushless);
  CANSparkMax rightBaseMotor = new CANSparkMax(Constants.ArmBaseConstants.baseRightMotorPort, CANSparkLowLevel.MotorType.kBrushless);

  AbsoluteEncoder leftEncoder;

  SparkPIDController leftMotorController;
  SparkPIDController rightMotorController;
  public double setpoint;
  public double prevSetpoint;

  public ArmBaseMovementSubsystem() {
    leftEncoder = leftBaseMotor.getAbsoluteEncoder();

    leftMotorController = leftBaseMotor.getPIDController();
    rightMotorController = rightBaseMotor.getPIDController();

    rightBaseMotor.follow(leftBaseMotor, true);
    rightMotorController.setOutputRange(-0.4, 0.4);
    leftMotorController.setOutputRange(-0.4, 0.4);
    
    leftBaseMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    leftBaseMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.ArmBaseConstants.kFSoftLimit);

    leftBaseMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    leftBaseMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.ArmBaseConstants.kBSoftLimit);

    //TODO figure out softlimit

    setpoint = 0;
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

  public void setPosition(double pos){
    if(pos>Constants.ArmBaseConstants.squishyLimit){setpoint = Constants.ArmBaseConstants.squishyLimit;}
    if(pos<Constants.ArmBaseConstants.squishyLimit2){setpoint = Constants.ArmBaseConstants.squishyLimit2;}
    else{setpoint = pos;}
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (setpoint != prevSetpoint) {
      leftMotorController.setReference(setpoint, CANSparkMax.ControlType.kPosition);
  }
    prevSetpoint = setpoint;





    SmartDashboard.putNumber("ArmBase Position", setpoint);
    SmartDashboard.putNumber("Previous ArmBase Position", prevSetpoint);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
