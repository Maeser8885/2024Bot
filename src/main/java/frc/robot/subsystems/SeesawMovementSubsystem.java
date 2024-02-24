// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//2 Moving, 2 on springs, 1 on side wheels 1 on intake wheels, 1 on moving seesaw
package frc.robot.subsystems;

import frc.robot.Constants.SeesawConstants;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SeesawMovementSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final CANSparkMax seeSawMotor = new CANSparkMax(SeesawConstants.seesawMotorPort, CANSparkLowLevel.MotorType.kBrushless);
  private AbsoluteEncoder seeSawEncoder;
  private SparkPIDController seeSawPID;
  private double setpoint;
  private double prevSetpoint;

  public SeesawMovementSubsystem() {
    seeSawEncoder = seeSawMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
    seeSawPID = seeSawMotor.getPIDController();
    //TODO figure out softLimit
    seeSawPID.setOutputRange(-0.4, 0.4);
    setpoint = 0.0;
  }
  //We might need a manual way to rotate in order to find setpositions

  void rotateToAngle(double angle){
    setpoint = angle;
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
      if (setpoint != prevSetpoint) {
            seeSawPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        }
        prevSetpoint = setpoint;

        SmartDashboard.putNumber("Setpoint for Seesaw", setpoint);
        SmartDashboard.putNumber("Current Seesaw val", seeSawEncoder.getPosition());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
