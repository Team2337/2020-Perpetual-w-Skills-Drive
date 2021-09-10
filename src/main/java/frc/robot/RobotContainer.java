// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.Agitator.*;
import frc.robot.commands.Climber.runClimberJoystick;
import frc.robot.commands.KickerWheel.runControlPanelMode;
import frc.robot.commands.KickerWheel.stopKicker;
import frc.robot.commands.Serializer.*;
import frc.robot.commands.Shooter.stopShooter;
import frc.robot.commands.ShooterSystem.*;
import frc.robot.commands.Vision.*;
import frc.robot.commands.auto.GalacticSearch;
import frc.robot.commands.auto.LPathTrajectory;
import frc.robot.commands.auto.MotionMagicCommand;
import frc.robot.commands.auto.calibration.StraightLineTest10Ft;
import frc.robot.commands.auto.calibration.StraightLineTest10Ft0;
import frc.robot.commands.auto.calibration.StraightLineTest10Ft1;
import frc.robot.commands.commandgroups.CGGalaticSearchBlueA;
import frc.robot.commands.commandgroups.CGGalaticSearchBlueB;
import frc.robot.commands.commandgroups.CGGalaticSearchRedA;
import frc.robot.commands.commandgroups.CGGalaticSearchRedB;
import frc.robot.commands.auto.autonav.BarrelRacing;
import frc.robot.commands.auto.autonav.BarrelRacing2;
import frc.robot.commands.auto.autonav.Bounce;
import frc.robot.commands.auto.autonav.Slalom;
import frc.robot.commands.auto.autonav.Slalom2;
import frc.robot.commands.swerve.*;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public final XboxController driverController = new XboxController(0);
  public final XboxController operatorController = new XboxController(1);
  public final XboxController operatorControls = new XboxController(2);

  /* --- Subsystems --- */
  public PixyCam2Wire pixy = new PixyCam2Wire(Constants.PIXY_ANALOG, Constants.PIXY_DIGITAL);

  public Pigeon pigeon = new Pigeon();
  public SwerveDrivetrain swerveDrivetrain = new SwerveDrivetrain(pigeon);
  public Intake intake = new Intake();
  public Agitator agitator = new Agitator();
  public Climber climber = new Climber();
  public KickerWheel kickerWheel = new KickerWheel();
  public Shooter shooter = new Shooter();
  public Vision vision = new Vision();
  public Serializer serializer = new Serializer();
  public OperatorAngleAdjustment operatorAngleAdjustment = new OperatorAngleAdjustment();
  
  
  private final SendableChooser<Command> autonChooser = new SendableChooser<>();


  


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    swerveDrivetrain.setDefaultCommand(new SwerveDriveCommand(swerveDrivetrain, driverController, operatorController));
    climber.setDefaultCommand(new runClimberJoystick(climber, operatorController));
    serializer.setDefaultCommand(new serializerBallControl(serializer, agitator, shooter, driverController, operatorController));
  
    // Configure the button bindings
    configureButtonBindings();

    

    // Resets the pigeon to 0
    pigeon.resetPidgey();

    resetDrivetrain();

    autonChooser.setDefaultOption("Do Nothing", new WaitCommand(15));
    try { autonChooser.addOption("Barrel Racing", new BarrelRacing(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("Barrel Racing 2 (Centr)", new BarrelRacing2(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("Bounce", new Bounce(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("Slalom", new Slalom(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("Slalom 2 (Centr)", new Slalom2(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("StraightLineTest10Ft", new StraightLineTest10Ft(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("StraightLineTest10Ft0", new StraightLineTest10Ft0(swerveDrivetrain)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("StraightLineTest10Ft1", new StraightLineTest10Ft1(swerveDrivetrain));} catch (IOException e) { e.printStackTrace(); }


    autonChooser.addOption("LPathCommand", new LPathTrajectory(swerveDrivetrain));
    autonChooser.addOption("Motion Magic (10ft)", new MotionMagicCommand(new Translation2d(10, 5), swerveDrivetrain));
    autonChooser.addOption("Motion Magic (L-10ft)", new SequentialCommandGroup(
      new MotionMagicCommand(new Translation2d(5, 2.5), swerveDrivetrain),
      new MotionMagicCommand(new Translation2d(10, 5), swerveDrivetrain)
    ));
    autonChooser.addOption("Motion Magic (L-10ft 2)",
        new SequentialCommandGroup(new MotionMagicCommand(new Translation2d(5, 0), swerveDrivetrain),
        new MotionMagicCommand(new Translation2d(10, 5), swerveDrivetrain)));
    autonChooser.addOption("Motion Magic (Tuning 1)", new SequentialCommandGroup(
        new MotionMagicCommand(new Translation2d(5, 0), swerveDrivetrain)
    ));
    autonChooser.addOption("Motion Magic (Tuning 2)", new SequentialCommandGroup(
        new MotionMagicCommand(new Translation2d(10, 0), swerveDrivetrain),
        new MotionMagicCommand(new Translation2d(5, 0), swerveDrivetrain),
        new MotionMagicCommand(new Translation2d(10, 0), swerveDrivetrain)
    ));
    autonChooser.addOption("Motion Magic (Tuning 3)", new SequentialCommandGroup(
        new MotionMagicCommand(new Translation2d(-5, 0), swerveDrivetrain)
    ));

    autonChooser.addOption("Galactic Search", new GalacticSearch(pixy, swerveDrivetrain));
    // try { autonChooser.addOption("Galatic Search Red A", new GalacticSearchRedA(swerveDrivetrain).beforeStarting(new SetIntakeSpeed(intake, 1).withTimeout(2); } catch (IOException e) { e.printStackTrace(); }
    // try { autonChooser.addOption("Galatic Search Red B", new GalacticSearchRedB(swerveDrivetrain).beforeStarting(() -> intake.setIntakeSpeed(1), intake));} catch (IOException e) { e.printStackTrace(); }
    // try { autonChooser.addOption("Galatic Search Blue A", new GalacticSearchBlueA(swerveDrivetrain).beforeStarting(() -> intake.setIntakeSpeed(1), intake));} catch (IOException e) { e.printStackTrace(); }
    // try { autonChooser.addOption("Galatic Search Blue B", new GalacticSearchBlueB(swerveDrivetrain).beforeStarting(() -> intake.setIntakeSpeed(1), intake));} catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("CGGalatic Search Red A", new CGGalaticSearchRedA(swerveDrivetrain, intake)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("CGGalatic Search Red B", new CGGalaticSearchRedB(swerveDrivetrain, intake)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("CGGalatic Search Blue A", new CGGalaticSearchBlueA(swerveDrivetrain, intake)); } catch (IOException e) { e.printStackTrace(); }
    try { autonChooser.addOption("CGGalatic Search Blue B", new CGGalaticSearchBlueB(swerveDrivetrain, intake)); } catch (IOException e) { e.printStackTrace(); }
  }

  public void resetDrivetrain() {
    swerveDrivetrain.resetOdometry();
    swerveDrivetrain.resetDriveMotors();
    swerveDrivetrain.resetAngleMotors();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Driver Left Bumper is used for field-oriented drive - held for true, released
    // for false

    final JoystickButton greenA = new JoystickButton(driverController, XboxController.Button.kA.value);
    final JoystickButton redB = new JoystickButton(driverController, XboxController.Button.kB.value);
    final JoystickButton blueX = new JoystickButton(driverController, XboxController.Button.kX.value);
    

    final JoystickButton bumperRight = new JoystickButton(driverController, XboxController.Button.kBumperRight.value);
    final JoystickButton bumperLeft = new JoystickButton(driverController, XboxController.Button.kBumperLeft.value);
    final JoystickButton back = new JoystickButton(driverController, XboxController.Button.kBack.value);
    final JoystickButton start = new JoystickButton(driverController, XboxController.Button.kStart.value);
    final Button dr_TriggerLeft = new Button(() -> driverController.getRawAxis(2) > .5);  //TODO: find the right axis and make sure it is positive. 
    final Button dr_TriggerRight = new Button(() -> driverController.getRawAxis(3) > -.5);  //TODO: find the right axis


    dr_TriggerRight.whenPressed(() -> serializer.setDrControlTrue());
    dr_TriggerRight.whenReleased(() -> serializer.setDrControlFalse());
/*
    //Drive motor controls
    greenA.whenPressed(() -> swerveDrivetrain.resetDriveMotors());
    // greenA.whenPressed(new InstantCommand(() -> swerveDrivetrain.resetDriveEncoders())));
    redB.whenPressed(() -> swerveDrivetrain.resetOdometry());

    // Intake controls
    bumperRight.whenPressed(new SetIntakeSpeed(intake, 0.75));
    bumperRight.whenReleased(new SetIntakeSpeed(intake, 0));

    bumperLeft.whenPressed(new SetIntakeSpeed(intake, -0.25));
    bumperLeft.whenReleased(new SetIntakeSpeed(intake, 0));

    SmartDashboard.putData("AutonChooser", autonChooser);
    // SmartDashboard.putData("Reset Drive Encoder", new InstantCommand(() -> swerveDrivetrain.resetDriveEncoders())));
*/
bumperLeft.whenPressed(new ChangeGyroAngleOffset(operatorAngleAdjustment, true));
bumperLeft.whenReleased(new ChangeGyroAngleOffset(operatorAngleAdjustment, false));

bumperRight.whenPressed(new runSerializer(serializer, Constants.SERIALIZERDRIVERFORWARDSPEED));
bumperRight.whenReleased(new stopSerializer(serializer));


/*
        // Slow rotates to the right
        redB         .whenPressed(new setSlowRotateMode(operatorAngleAdjustment, true, -Constants.Swerve.SLOWROTATESPEED));
        redB         .whenReleased(new setSlowRotateMode(operatorAngleAdjustment, false, 0));
        
        // Slow rotates to the left
        blueX         .whenPressed(new setSlowRotateMode(operatorAngleAdjustment, true, Constants.Swerve.SLOWROTATESPEED));
        blueX         .whenReleased(new setSlowRotateMode(operatorAngleAdjustment, false, 0));
*/
        /////////povUp.whenPressed(new ResetGyro(Pigeon));

        back.whenPressed(new ChangeVisionAngleOffset(operatorAngleAdjustment, true));
        back.whenReleased(new ChangeVisionAngleOffset(operatorAngleAdjustment, false));

        start.whenPressed(new setBallTracking(operatorAngleAdjustment, true));
        start.whenReleased(new setBallTracking(operatorAngleAdjustment, false));


// ***********************  Operator ******************************

final JoystickButton op_greenA = new JoystickButton(operatorController, XboxController.Button.kA.value);
final JoystickButton op_redB = new JoystickButton(operatorController, XboxController.Button.kB.value);
final JoystickButton op_blueX = new JoystickButton(operatorController, XboxController.Button.kX.value);
final JoystickButton op_yellowY = new JoystickButton(operatorController, XboxController.Button.kY.value);
final JoystickButton op_start = new JoystickButton(operatorController, XboxController.Button.kStart.value);
final JoystickButton op_back = new JoystickButton(operatorController, XboxController.Button.kBack.value);
final JoystickButton op_bumperleft = new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value);
final JoystickButton op_bumperRight = new JoystickButton(operatorController, XboxController.Button.kBumperRight.value);
final JoystickButton op_leftStickButton = new JoystickButton(operatorController, XboxController.Button.kStickLeft.value);
final JoystickButton op_rightStickButton = new JoystickButton(operatorController, XboxController.Button.kStickRight.value);
final Button op_TriggerLeft = new Button(() -> operatorController.getRawAxis(2) > .5);  //TODO: find the right axis and make sure it is positive. 
final Button op_TriggerRight = new Button(() -> operatorController.getRawAxis(3) > -.5);  //TODO: find the right axis
final POVButton op_povUp = new POVButton(operatorController, 0);
final POVButton op_povLeft = new POVButton(operatorController, 90);
final POVButton op_povRight = new POVButton(operatorController, 180);
final POVButton op_povDown = new POVButton(operatorController, 270);







//op_TriggerLeft.whenPressed(new opSerializerBallControl(serializer, agitator));
//op_TriggerLeft.whenReleased(new stopSerializer(serializer).andThen(new stopAgitator(agitator)));
//op_TriggerRight.whenPressed(() -> serializer.setOpControlTrue());
//op_TriggerRight.whenReleased(() -> serializer.setOpControlFalse());




op_TriggerRight   .whenPressed(new runIntake(intake, Constants.INTAKEFORWARDSPEED));
op_TriggerRight   .whenReleased(new stopIntake(intake));

op_TriggerRight.whenPressed(new runAgitator(agitator, Constants.AGITATORSPEED));
op_TriggerRight.whenReleased(new stopAgitator(agitator));




op_bumperRight    .whenPressed(new runIntake(intake, -Constants.INTAKEFORWARDSPEED));
op_bumperRight    .whenReleased(new stopIntake(intake));

op_bumperleft.whenPressed(new feedSystemReverse(agitator, serializer));
op_bumperleft.whenReleased(new feedSystemStop(agitator, serializer));

op_leftStickButton.whenPressed(new runSerializer(serializer, -Constants.SERIALIZEROPERATORFORWARDSPEED));
op_leftStickButton.whenReleased(new stopSerializer(serializer));

op_start.whenPressed(new adjustSerializer(serializer, Constants.SERIALIZERREGRESSIONDISTANCE).withTimeout(0.5));
op_start.whenReleased(new limeLightLEDOn(vision).andThen(new shooterSystemOn(shooter, kickerWheel)));

op_back. whenPressed(new shooterSystemOff(shooter, kickerWheel).andThen(new stopShooter(shooter)).andThen(new limeLightLEDOff(vision)));

op_yellowY.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "farShot", shooter, vision, kickerWheel, swerveDrivetrain));
op_redB.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "nearShot", shooter, vision, kickerWheel, swerveDrivetrain));
op_blueX.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "frontTrenchShot", shooter, vision, kickerWheel, swerveDrivetrain));
op_greenA.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "frontTrenchRunShot", shooter, vision, kickerWheel, swerveDrivetrain));

op_povUp.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "0", shooter, vision, kickerWheel, swerveDrivetrain));
op_povRight.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "90", shooter, vision, kickerWheel, swerveDrivetrain));
op_povDown.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "180", shooter, vision, kickerWheel, swerveDrivetrain));
op_povLeft.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "270", shooter, vision, kickerWheel, swerveDrivetrain));






//op_leftStickButton.whenPressed(new runSerializer(serializer, -Constants.SERIALIZEROPERATORFORWARDSPEED));
//op_leftStickButton.whenReleased(new stopSerializer(serializer));



// ***********************  Operator Station ******************************

/*
    public JoystickButton  GreenButton  = new JoystickButton(this, 1);  // greenA
    public JoystickButton  RedButton    = new JoystickButton(this, 2);  red B
    public JoystickButton  WhiteButton  = new JoystickButton(this, 7);  back
    public JoystickButton  YellowButton = new JoystickButton(this, 8);  start
    public JoystickButton  BlueButton   = new JoystickButton(this, 9);  left stick
    public JoystickButton  BlackButton  = new JoystickButton(this, 10);  right stick

    public JoystickButton  ClearSwitch  = new JoystickButton(this, 3);  Blue x
    public JoystickButton  YellowSwitch = new JoystickButton(this, 4); yellow y
    public JoystickButton  BlueSwitch   = new JoystickButton(this, 5);  bumper left
    public JoystickButton  BlackSwitch  = new JoystickButton(this, 6);  bumper right
    */


final JoystickButton blackSwitch = new JoystickButton(operatorControls, XboxController.Button.kBumperRight.value); 
final JoystickButton blackButton = new JoystickButton(operatorControls, XboxController.Button.kStickRight.value);  
final JoystickButton blueButton = new JoystickButton(operatorControls, XboxController.Button.kStickLeft.value);
final JoystickButton blueSwitch = new JoystickButton(operatorControls, XboxController.Button.kBumperLeft.value);
final JoystickButton yellowButton = new JoystickButton(operatorControls, XboxController.Button.kStart.value);
final JoystickButton yellowSwitch = new JoystickButton(operatorControls, XboxController.Button.kY.value);

/*
blackSwitch.whenPressed(new activateClimber(climber, true));
blackSwitch.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "climbing"));
blackSwitch.whenReleased(new activateClimber(climber, false));

blackButton.whenPressed(new runClimber(climber, 177500, false));
blackButton.whenReleased(new runClimber(climber, 177500, true));

blueButton.whenPressed(new runClimber(climber, 50000, false));
blueButton.whenReleased(new runClimber(climber, 50000, true));
*/

/*
op_greenA.whenPressed(new activateClimber(climber, true));
//op_greenA.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "climbing"));
op_greenA.whenReleased(new activateClimber(climber, false));

op_redB.whenPressed(new runClimber(climber, 177500, false));
op_redB.whenReleased(new runClimber(climber, 177500, true));

op_blueX.whenPressed(new runClimber(climber, 50000, false));
op_blueX.whenReleased(new runClimber(climber, 50000, true));
*/
yellowButton.whenPressed(new runControlPanelMode(kickerWheel));
yellowButton.whenReleased(new stopKicker(kickerWheel));

blueSwitch.whenPressed(new ChangeVisionAngleOffset(operatorAngleAdjustment, false));


//YellowSwitch.whenPressed(new deployHyperLoop(Robot.Servo66));
//YellowSwitch.whenReleased(new retractHyperLoop(Robot.Servo66));


  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }

  public boolean getOpRightY() {
     return operatorController.getYButton();

  }
  public double getOpRightStickYValue()  {
    return operatorController.getY(Hand.kRight);
  }

  public boolean getDriveTriggerRight() {
    return (driverController.getTriggerAxis(Hand.kRight)> .5) ;
  }

  public boolean getOpTriggerRight() {
    return (driverController.getTriggerAxis(Hand.kRight)> .5) ;
  }

  public boolean getOpTriggerLeft() {
    return (operatorController.getTriggerAxis(Hand.kRight)> .5) ;
  }

}
