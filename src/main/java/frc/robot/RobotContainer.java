// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.Agitator.stopAgitator;
import frc.robot.commands.Climber.activateClimber;
import frc.robot.commands.Climber.runClimber;
import frc.robot.commands.Climber.runClimberJoystick;
import frc.robot.commands.KickerWheel.runControlPanelMode;
import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.KickerWheel.stopKicker;
import frc.robot.commands.Serializer.adjustSerializer;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.Serializer.serializerBallControl;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.commands.Shooter.stopShooter;
import frc.robot.commands.ShooterSystem.shooterSystemOff;
import frc.robot.commands.ShooterSystem.shooterSystemOn;
import frc.robot.commands.Vision.limeLightLEDOff;
import frc.robot.commands.Vision.limeLightLEDOn;
import frc.robot.commands.Vision.setBallTracking;
import frc.robot.commands.auto.AutoStrafeWithPixy;
import frc.robot.commands.auto.commandgroups.nineball.CenterGoal9Ball;
import frc.robot.commands.auto.commandgroups.nineball.CenterGoal9BallTrench;
import frc.robot.commands.auto.commandgroups.nineball.CenterGoal9BallTurn;
import frc.robot.commands.auto.commandgroups.nineball.CenterGoalBack9BallGenerator3Ball;
import frc.robot.commands.auto.commandgroups.sixball.CenterFeedLeftTRGrab3GenRGrab2Score5;
import frc.robot.commands.auto.commandgroups.sixball.CenterFeedLeftTRGrab3Score3;
import frc.robot.commands.auto.commandgroups.sixball.CenterFeedRightTRGrab3GenRGrab2Score5;
import frc.robot.commands.auto.commandgroups.sixball.CenterFeedRightTRGrab3Score3;
import frc.robot.commands.auto.commandgroups.sixball.CenterGoal6Ball;
import frc.robot.commands.auto.commandgroups.threeball.CenterGoal3Ball;
import frc.robot.commands.auto.commandgroups.threeball.CenterTRGrab3Score3;
import frc.robot.commands.intake.runIntake;
import frc.robot.commands.intake.stopIntake;
import frc.robot.commands.swerve.ChangeGyroAngleOffset;
import frc.robot.commands.swerve.ChangeVisionAngleOffset;
import frc.robot.commands.swerve.SetGyroAngleOffset;
import frc.robot.commands.swerve.SwerveDriveCommand2;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.PixyCam2Wire;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;


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
  public Intake intake = new Intake();
  public Agitator agitator = new Agitator();
  public Climber climber = new Climber();
  public KickerWheel kickerWheel = new KickerWheel();
  public Shooter shooter = new Shooter();
  public Vision vision = new Vision();
  public Serializer serializer = new Serializer();
  public OperatorAngleAdjustment operatorAngleAdjustment = new OperatorAngleAdjustment();
  public SwerveDrivetrain swerveDrivetrain = new SwerveDrivetrain(pigeon);
  
  
  private final SendableChooser<String> autonChooser = new SendableChooser<String>();
  private final SendableChooser<String> delayChooser = new SendableChooser<String>();


  


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //swerveDrivetrain.setDefaultCommand(new SwerveDriveCommand(swerveDrivetrain, driverController, operatorController));
    swerveDrivetrain.setDefaultCommand(new SwerveDriveCommand2(swerveDrivetrain, driverController, operatorController, shooter, operatorAngleAdjustment, vision, pigeon));
    climber.setDefaultCommand(new runClimberJoystick(climber, operatorController));
    serializer.setDefaultCommand(new serializerBallControl(serializer, agitator, shooter, driverController, operatorController));
  
    // Configure the button bindings
    configureButtonBindings();

    

    // Resets the pigeon to 0
    pigeon.resetPidgey();

    resetDrivetrain();

    autonChooser.setDefaultOption("Do Nothing", "default");
    autonChooser.addOption("9 Ball - Back Up Straight", "9 Ball - Back Up Straight");
    autonChooser.addOption("9 Ball - Back Up - Turn 90", "9 Ball - Back Up - Turn 90");
    autonChooser.addOption("9 Ball - Drive to Trench", "9 Ball - Drive to Trench");
    autonChooser.addOption("6 Ball - Back Up Straight", "6 Ball - Back Up Straight");
    autonChooser.addOption("6 Ball - Partner Left - 3 Trench", "6 Ball - Partner Left - 3 Trench");
    autonChooser.addOption("6 Ball - Partner Right - 3 Trench", "6 Ball - Partner Right - 3 Trench");
    autonChooser.addOption("3 Ball - Trench", "3 Ball - Trench");
  }


    /*
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

  */

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
bumperLeft.whenPressed(new ChangeGyroAngleOffset(operatorAngleAdjustment, true, vision, pigeon));
bumperLeft.whenReleased(new ChangeGyroAngleOffset(operatorAngleAdjustment, false, vision, pigeon));

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

        back.whenPressed(new ChangeVisionAngleOffset(operatorAngleAdjustment, true, pigeon));
        back.whenReleased(new ChangeVisionAngleOffset(operatorAngleAdjustment, false, pigeon));

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
final POVButton op_povLeft = new POVButton(operatorController, 270);
final POVButton op_povRight = new POVButton(operatorController, 90);
final POVButton op_povDown = new POVButton(operatorController, 180);







//op_TriggerLeft.whenPressed(new opSerializerBallControl(serializer, agitator));
//op_TriggerLeft.whenReleased(new stopSerializer(serializer).andThen(new stopAgitator(agitator)));
//op_TriggerRight.whenPressed(() -> serializer.setOpControlTrue());
//op_TriggerRight.whenReleased(() -> serializer.setOpControlFalse());




op_TriggerRight   .whenPressed(new runIntake(intake, Constants.INTAKEFORWARDSPEED));
op_TriggerRight   .whenReleased(new stopIntake(intake));

op_TriggerRight.whenPressed(new runAgitator(agitator, Constants.AGITATORSPEED));
op_TriggerRight.whenReleased(new stopAgitator(agitator));




op_bumperRight    .whenPressed(new runIntake(intake, Constants.INTAKEFORWARDSPEED));
op_bumperRight    .whenReleased(new stopIntake(intake));

op_bumperleft    .whenPressed(new runIntake(intake, Constants.INTAKEREVERSESPEED));
op_bumperleft    .whenReleased(new stopIntake(intake));
/*
op_bumperleft.whenPressed(new feedSystemReverse(agitator, serializer));
op_bumperleft.whenReleased(new feedSystemStop(agitator, serializer));
*/

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
//op_povLeft.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "270", shooter, vision, kickerWheel, swerveDrivetrain));







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
op_povLeft.whenPressed(new activateClimber(climber, true));
op_povLeft.whenPressed(new SetGyroAngleOffset(operatorAngleAdjustment, "climbing", shooter, vision, kickerWheel, swerveDrivetrain));
op_povLeft.whenReleased(new activateClimber(climber, false)); */

op_povLeft.whenPressed(new runControlPanelMode(kickerWheel));

blackButton.whenPressed(new runClimber(climber, 177500, false));
blackButton.whenReleased(new runClimber(climber, 177500, true));

blueButton.whenPressed(new runClimber(climber, 50000, false));
blueButton.whenReleased(new runClimber(climber, 50000, true));


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

blueSwitch.whenPressed(new ChangeVisionAngleOffset(operatorAngleAdjustment, false, pigeon));


//YellowSwitch.whenPressed(new deployHyperLoop(Robot.Servo66));
//YellowSwitch.whenReleased(new retractHyperLoop(Robot.Servo66));


delayChooser.setDefaultOption("0", "0");
delayChooser.addOption("0.5", "0.5");
delayChooser.addOption("1", "1");
delayChooser.addOption("1.5", "1.5");
delayChooser.addOption("2", "2");
delayChooser.addOption("2.5", "2.5");
delayChooser.addOption("3", "3");
delayChooser.addOption("3.5", "3.5");
delayChooser.addOption("4", "4");
delayChooser.addOption("4.5", "4.5");
delayChooser.addOption("5", "5");

SmartDashboard.putData("AutonChooser", autonChooser);
SmartDashboard.putData("DelayChooser", delayChooser);

  }

  public double getDelay() {
    double delay = 0;
        switch (getDelayVariable()) {
            case "0.5":
            delay = 0.5;
            break;
            case "1":
            delay = 1;
            break;
            case "1.5":
            delay = 1.5;
            break;
            case "2":
            delay = 2;
            break;
            case "2.5":
            delay = 2.5;
            break;
            case "3":
            delay = 3;
            break;
            case "3.5":
            delay = 3.5;
            break;
            case "4":
            delay = 4;
            break;
            case "4.5":
            delay = 4.5;
            break;
            case "5":
            delay = 5;
            break;
            default:
            delay = 0;
            break;
        }
        return delay;
    }
    
    public Command getAutoCommand(double delay) {
      Command autonomousCommand;
    switch (getAutonomousCommand()) {
      case "9 Ball - Back Up Straight":
        autonomousCommand = new CenterGoal9Ball(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "9 Ball - Back Up - Turn 90":
        autonomousCommand = new CenterGoal9BallTurn(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "9 Ball - Drive to Trench":
        autonomousCommand = new CenterGoal9BallTrench(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "9 Ball - 3 Generator":
        autonomousCommand = new CenterGoalBack9BallGenerator3Ball(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - Back Up Straight":
        autonomousCommand = new CenterGoal6Ball(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - 3 Generator":
        autonomousCommand = new CenterGoalBack9BallGenerator3Ball(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - Partner Left - 3 Trench":
        autonomousCommand = new CenterFeedLeftTRGrab3Score3(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - Partner Right - 3 Trench":
        autonomousCommand = new CenterFeedRightTRGrab3Score3(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - Partner Left - 3 Trench - 2 Generator":
        autonomousCommand = new CenterFeedLeftTRGrab3GenRGrab2Score5(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "6 Ball - Partner Right - 3 Trench - 2 Generator":
        autonomousCommand = new CenterFeedRightTRGrab3GenRGrab2Score5(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;        
      case "3 Ball - Trench":
        autonomousCommand = new CenterTRGrab3Score3(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      case "3 Ball - Back Up":
        autonomousCommand = new CenterGoal3Ball(delay, intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision);
        break;
      default:
        autonomousCommand = new WaitCommand(15).withTimeout(15);
        break;
      
    }
      return autonomousCommand;
    }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  /*
  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
  */

  public String getAutonomousCommand() {
    return autonChooser.getSelected();
  }

  public String getDelayVariable() {
    return delayChooser.getSelected();
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
