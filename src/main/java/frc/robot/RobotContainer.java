// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.subsystems.ArmBaseMovementSubsystem;
import frc.robot.subsystems.ArmRetractionSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HookSubsystem;
import frc.robot.subsystems.SeesawMovementSubsystem;
import frc.robot.subsystems.ShootingAndIntakeSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  DriveSubsystem driveSubsystem = new DriveSubsystem();
  ShootingAndIntakeSubsystem shootingandIntakeSubsystem = new ShootingAndIntakeSubsystem();
  ArmBaseMovementSubsystem armBaseMovementSubsystem = new ArmBaseMovementSubsystem();
  SeesawMovementSubsystem seesawMovementSubsystem = new SeesawMovementSubsystem();
  ArmRetractionSubsystem armRetractionSubsystem = new ArmRetractionSubsystem();
  HookSubsystem hookSubsystem = new HookSubsystem();
  ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  
  // Replace with CommandPS4Controller or CommandJoystick if needed
  public static final CommandXboxController xboxController =
      new CommandXboxController(Constants.OperatorConstants.xboxControllerPort);

  public static final CommandJoystick joystickController = new CommandJoystick(Constants.OperatorConstants.joystickPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    setupDashboard();

    armBaseMovementSubsystem.setDefaultCommand(
      new RunCommand(()->{
        double positionToSet = xboxController.getRightX() * Constants.ArmBaseConstants.a_multiplicationControlFactor;

        if(armBaseMovementSubsystem.setpoint != positionToSet){
          armBaseMovementSubsystem.setPosition(positionToSet);
        }
      }, armBaseMovementSubsystem
      ));
      
      seesawMovementSubsystem.setDefaultCommand(
      new RunCommand(()->{
        double positionToSet = xboxController.getLeftX() * Constants.SeesawConstants.s_multiplicationControlFactor;

        if(armBaseMovementSubsystem.setpoint != positionToSet){
          armBaseMovementSubsystem.setPosition(positionToSet);
        }
      }, seesawMovementSubsystem));

driveSubsystem.setDefaultCommand(
  new RunCommand(()->{
    driveSubsystem.arcadeDrive(getThrottledY(), getThrottledTwist());
  }, driveSubsystem)
);
  }

  private double adjustThrottle(double throttle) {
    return -throttle/2 +1;
}

public double getThrottledY(){
  return joystickController.getY() * adjustThrottle(joystickController.getThrottle());
}

public double getThrottledTwist(){
  return joystickController.getTwist() * adjustThrottle(joystickController.getThrottle());
}


  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

      xboxController.rightTrigger().onTrue(
        new RunCommand(()->{shootingandIntakeSubsystem.shootNote();
        }, shootingandIntakeSubsystem));

      xboxController.leftTrigger().onTrue(new RunCommand(()->{
        shootingandIntakeSubsystem.intakeNote();
      }, shootingandIntakeSubsystem));

      Trigger retractTrigger = xboxController.leftBumper().and(xboxController.rightBumper());

      retractTrigger.onTrue(new RunCommand(()->{
        boolean extended = armRetractionSubsystem.getExtended();
        if(extended)armRetractionSubsystem.retractWinch();
        else{armRetractionSubsystem.extendWinch();}
      }, armRetractionSubsystem));

      Trigger climberTrigger = xboxController.leftStick().and(xboxController.rightStick());

      climberTrigger.onTrue(new RunCommand(()->{
        if(hookSubsystem.getExtended()){
          hookSubsystem.retractHook();
        }
        else{
          hookSubsystem.extendHook();
        }
      }, hookSubsystem));
      
      //TODO finish controls
      //x y a b presets for trap amp shooter and intake
  }

 RunCommand speakerCommand = new RunCommand(()->{
  armBaseMovementSubsystem.setPosition(Constants.AutoConstants.armBase);
  seesawMovementSubsystem.rotateToAngle(Constants.AutoConstants.speakerSeesaw);

 }, armBaseMovementSubsystem, seesawMovementSubsystem);
  
//TODO these are NOT right
//  public SequentialCommandGroup getFrontCommand(){
//     return new SequentialCommandGroup(
//       new DriveTimedCommand(2, 0.5, driveSubsystem),
//       new RotateAngleCommand(90, driveSubsystem),
//       speakerCommand,
//       new DriveTimedCommand(2, -0.5, driveSubsystem)
//     );
//  }

//  public SequentialCommandGroup getLeftCommand(){
//     return new SequentialCommandGroup(
//       new DriveTimedCommand(2, 0.5, driveSubsystem),
//       new RotateAngleCommand(90, driveSubsystem),
//       speakerCommand,
//       new DriveTimedCommand(2, -0.5, driveSubsystem)
//     );
//  }

//  public SequentialCommandGroup getRightCommand(){
//     return new SequentialCommandGroup(
//       new DriveTimedCommand(2, 0.5, driveSubsystem),
//       new RotateAngleCommand(90, driveSubsystem),
//       speakerCommand,
//       new DriveTimedCommand(2, -0.5, driveSubsystem)
//     );
//  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

public SendableChooser<SequentialCommandGroup> AutoChooser = new SendableChooser<SequentialCommandGroup>();

   public void setupDashboard()
   {
  // AutoChooser.setDefaultOption("Forward", getFrontCommand());
  // AutoChooser.addOption("Left", getLeftCommand());
  // AutoChooser.addOption("Right", getRightCommand());
  SmartDashboard.putData(AutoChooser);
  SmartDashboard.putNumber("THROTTLE", joystickController.getThrottle()/2+0.5);
  }

  public Command getAutonomousCommand() {
    SequentialCommandGroup returnVal =  AutoChooser.getSelected();
    AutoChooser.close();
    return returnVal;
  }
}

