// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.TeleopArcadeDriveCommand;
//import frc.robot.commands.DriveTimedCommand;
//import frc.robot.commands.RotateAngleCommand;
import frc.robot.commands.XboxControlledArm;
import frc.robot.commands.XboxControlledSeesaw;
import frc.robot.subsystems.ArmBaseMovementSubsystem;
import frc.robot.subsystems.ArmRetractionSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HookSubsystem;
import frc.robot.subsystems.SeesawMovementSubsystem;
import frc.robot.subsystems.ShootingAndIntakeSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  DriveSubsystem driveSubsystem = new DriveSubsystem();
  ShootingAndIntakeSubsystem shootingandIntakeSubsystem = new ShootingAndIntakeSubsystem();
  ArmBaseMovementSubsystem armBaseMovementSubsystem = new ArmBaseMovementSubsystem();
  SeesawMovementSubsystem seesawMovementSubsystem = new SeesawMovementSubsystem();
  ArmRetractionSubsystem armRetractionSubsystem = new ArmRetractionSubsystem();
  HookSubsystem hookSubsystem = new HookSubsystem();

  private final XboxControlledArm xboxControlledArm;
  private final XboxControlledSeesaw xboxControlledSeesaw;
  private final TeleopArcadeDriveCommand teleopArcadeDriveCommand;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final CommandXboxController xboxController = new CommandXboxController(
      Constants.OperatorConstants.xboxControllerPort);

  public final CommandJoystick joystickController = new CommandJoystick(Constants.OperatorConstants.joystickPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    setupDashboard();

    Supplier<Double> armJoystickSupplier = () -> xboxController.getRightX();
    xboxControlledArm = new XboxControlledArm(armBaseMovementSubsystem, armJoystickSupplier);
    armBaseMovementSubsystem.setDefaultCommand(xboxControlledArm);

    Supplier<Double> seesawJoystickSupplier = () -> xboxController.getLeftX();
    xboxControlledSeesaw = new XboxControlledSeesaw(seesawMovementSubsystem, seesawJoystickSupplier);
    seesawMovementSubsystem.setDefaultCommand(xboxControlledSeesaw);

    Supplier<Double> xSpeedSupplier = () -> joystickController.getY();
    Supplier<Double> rotationSupplier = () -> joystickController.getTwist();
    teleopArcadeDriveCommand = new TeleopArcadeDriveCommand(driveSubsystem, xSpeedSupplier, rotationSupplier);
    driveSubsystem.setDefaultCommand(teleopArcadeDriveCommand);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    xboxController.rightTrigger().toggleOnTrue(new RunCommand(() -> {
      shootingandIntakeSubsystem.shootNote();
    }, shootingandIntakeSubsystem));

    xboxController.rightTrigger().toggleOnFalse(new InstantCommand(() -> {
      shootingandIntakeSubsystem.stopShooting();
    }, shootingandIntakeSubsystem));

    xboxController.leftTrigger().toggleOnTrue(new InstantCommand(() -> {
      shootingandIntakeSubsystem.intakeNote();
    }, shootingandIntakeSubsystem));

    xboxController.leftTrigger().toggleOnFalse(new InstantCommand(() -> {
      shootingandIntakeSubsystem.stopIntake();
    }, shootingandIntakeSubsystem));

    Trigger retractTrigger = xboxController.leftBumper().and(xboxController.rightBumper());

    retractTrigger.onTrue(new RunCommand(() -> {
      boolean extended = armRetractionSubsystem.getExtended();
      if (extended)
        armRetractionSubsystem.retractWinch();
      else {
        armRetractionSubsystem.extendWinch();
      }
    }, armRetractionSubsystem));

    Trigger climberTrigger = xboxController.leftStick().and(xboxController.rightStick());

    climberTrigger.onTrue(new RunCommand(() -> {
      if (hookSubsystem.getExtended()) {
        hookSubsystem.retractHook();
      } else {
        hookSubsystem.extendHook();
      }
    }, hookSubsystem));

    // TODO finish controls
    // x = speaker
    // y = amp
    // a = ground intake
    // b = human intake

    xboxController.x().onTrue(new RunCommand(() -> {
      armBaseMovementSubsystem.setPosition(Constants.ArmBaseConstants.speakerPos);
      seesawMovementSubsystem.rotateToAngle(Constants.SeesawConstants.speakerPos);
      // extend maybe??
    }, armBaseMovementSubsystem, seesawMovementSubsystem));

    xboxController.y().onTrue(new RunCommand(() -> {
      armBaseMovementSubsystem.setPosition(Constants.ArmBaseConstants.ampPos);
      seesawMovementSubsystem.rotateToAngle(Constants.SeesawConstants.ampPos);
      // extend maybe??
    }, armBaseMovementSubsystem, seesawMovementSubsystem));

    xboxController.a().onTrue(new RunCommand(() -> {
      armBaseMovementSubsystem.setPosition(Constants.ArmBaseConstants.groundIntakePos);
      seesawMovementSubsystem.rotateToAngle(Constants.SeesawConstants.groundIntakePos);
      // extend maybe??
    }, armBaseMovementSubsystem, seesawMovementSubsystem));

    xboxController.b().onTrue(new RunCommand(() -> {
      armBaseMovementSubsystem.setPosition(Constants.ArmBaseConstants.humanIntakePos);
      seesawMovementSubsystem.rotateToAngle(Constants.SeesawConstants.humanIntakePos);
      // extend maybe??
    }, armBaseMovementSubsystem, seesawMovementSubsystem));
  }

  RunCommand speakerCommand = new RunCommand(() -> {
    armBaseMovementSubsystem.setPosition(Constants.AutoConstants.armBase);
    seesawMovementSubsystem.rotateToAngle(Constants.AutoConstants.speakerSeesaw);

  }, armBaseMovementSubsystem, seesawMovementSubsystem);

  //

  // public SequentialCommandGroup getFrontCommand(){
  // return new SequentialCommandGroup(
  // new
  // DriveTimedCommand(Constants.AutoConstants.forwardDistance/Constants.AutoConstants.velocity,
  // 1, driveSubsystem),
  // speakerCommand,
  // new
  // DriveTimedCommand(Constants.AutoConstants.forwardDistance/Constants.AutoConstants.velocity,
  // -1, driveSubsystem)
  // );
  // }

  // public SequentialCommandGroup getLeftCommand(){
  // return new SequentialCommandGroup(
  // new DriveTimedCommand(Constants.AutoConstants.beforeLeftDist, 1,
  // driveSubsystem),
  // new RotateAngleCommand(Constants.AutoConstants.leftTurnAngle,
  // driveSubsystem),
  // new
  // DriveTimedCommand(Constants.AutoConstants.afterLeftDist/Constants.AutoConstants.velocity,
  // 0, driveSubsystem),
  // speakerCommand,
  // new DriveTimedCommand(-Constants.AutoConstants.afterLeftDist, 1,
  // driveSubsystem),
  // new RotateAngleCommand(-Constants.AutoConstants.leftTurnAngle,
  // driveSubsystem),
  // new DriveTimedCommand(Constants.AutoConstants.beforeLeftDist, 1,
  // driveSubsystem)
  // );
  // }

  // public SequentialCommandGroup getRightCommand(){
  // return new SequentialCommandGroup(
  // new DriveTimedCommand(Constants.AutoConstants.beforeRightDist, 1,
  // driveSubsystem),
  // new RotateAngleCommand(Constants.AutoConstants.rightTurnAngle,
  // driveSubsystem),
  // new
  // DriveTimedCommand(Constants.AutoConstants.afterRightDist/Constants.AutoConstants.velocity,
  // 0, driveSubsystem),
  // speakerCommand,
  // new DriveTimedCommand(-Constants.AutoConstants.afterRightDist, 1,
  // driveSubsystem),
  // new RotateAngleCommand(-Constants.AutoConstants.rightTurnAngle,
  // driveSubsystem),
  // new DriveTimedCommand(Constants.AutoConstants.beforeRightDist, 1,
  // driveSubsystem)
  // );
  // }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public SendableChooser<SequentialCommandGroup> AutoChooser = new SendableChooser<SequentialCommandGroup>();

  public void setupDashboard() {
    // AutoChooser.setDefaultOption("Forward", getFrontCommand());
    // AutoChooser.addOption("Left", getLeftCommand());
    // AutoChooser.addOption("Right", getRightCommand());
    SmartDashboard.putData(AutoChooser);
    SmartDashboard.putNumber("THROTTLE", joystickController.getThrottle() / 2 + 0.5);
  }

  public Command getAutonomousCommand() {
    SequentialCommandGroup returnVal = AutoChooser.getSelected();
    AutoChooser.close();
    return returnVal;
  }
}
