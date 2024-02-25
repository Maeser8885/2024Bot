// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

 //TODO change all the ports
public final class Constants {
  public static class OperatorConstants {
    public static final int xboxControllerPort = 0;
    public static final int joystickPort = 1;
  }
  public static class MotorConstants {
    public static final int kblMotorPort = 2;
    public static final int kbrMotorPort = 1;
    public static final int kflMotorPort = 3;
    public static final int kfrMotorPort = 0;
  }

  public static class SeesawConstants{
    public static final int seesawMotorPort = 4;  
    //TODO THESE R WRONG LIKE IT WILL LITERALLY BREAK ROBOT IF WE DONT CHANGE
    public static final double shootingPos = 10.0;
    public static final double zerodegreePos = 0.0;
    public static final double groundIntakePos = -5.0;
    public static final double humanIntakePos = -10.0;
    public static final double s_multiplicationControlFactor = 1.0;

  }
  public static class shootingAndIntakeConstants{
    public static final int kshootMotorPort = 5;
    public static final double shootspeed = 1;
    public static final int intakeMotor = 6;
    public static final double intakeSpeed = 0.1;
    //TODO: Change these so it shoots good amount of time :)
    public static final double secondsToShoot = 1;
    public static double secondsToIntake = 1;
  }
  public static class RetractionConstants{
    public static final int winchMotorPort = 9;
    public static final double winchSpeed = 0.4;
  }

  public static class ArmBaseConstants{
    public static final double shootingPos = 10.0;
    public static final double zerodegreePos = 0.0;
    public static final double groundIntakePos = -5.0;
    public static final double humanIntakePos = -10.0;
    public static final int baseLeftMotorPort = 7;
    public static final int baseRightMotorPort = 8;
    public static final double a_multiplicationControlFactor = 1.0;
  }

  public static class HookConstants{
    //TODO: Change this so it works
    public static final int hookMotorPort = 10;
    public static double hookRetractionSpeed = 1;
    public static double hookExtensionSpeed = 1;

  }

  public static class AutoConstants{
    public static final double turnSpeed = 0.5;
    //TODO THIS WILL ALSO PROBABLY BREAK THE ROBOT
    public static final double speakerSeesaw = 0.5;
    public static final double armBase = 0.5;

  }
}
