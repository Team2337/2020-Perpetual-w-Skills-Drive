package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Utilities;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;

/**
 * Command running the swerve calculations with the joystick
 *
 * @see SwerveDrivetrain
 * @author Bryce G.
 * @category SWERVE
 */
public class SwerveDriveCommand2 extends CommandBase {

  private final SwerveDrivetrain drivetrain;
  private final XboxController driverController;
  private final XboxController operatorController;
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private final Shooter shooter;
  private final Vision vision;
  private final Pigeon pigeon;

  private double rotation = 0;
  private double error;
  private double kP;
  
  /** Rotation value of the previous iteration */
  private double lastRotation;
  /** Deadband for the rotational input  */
  private double rotationDeadband = 0.1;
  /** Rotational P while not rotating */
  private double stationaryP = 0.009;
  /** Rotational P while rotating */
  private double movingP = 0.002; //0.007

  private boolean stopRotating = false;
  private double i = 0;
  private double rotationLimiter = 0.15;


  /**
   * Command running the swerve calculations with the joystick
   *
   * @param subsystem - SwerveDrivetrain subsystem object
   */
  public SwerveDriveCommand2(SwerveDrivetrain drivetrain, XboxController driverController, XboxController operatorController, Shooter shooter, OperatorAngleAdjustment operatorAngleAdjustment, Vision vision, Pigeon pigeon) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

    this.driverController = driverController;
    this.operatorController = operatorController;
    this.shooter = shooter;
    this.vision = vision;
    this. operatorAngleAdjustment = operatorAngleAdjustment;
    this.pigeon = pigeon;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    // Inverting this Y value because Xbox controllers return negative values when we push forward.
    double forward = -driverController.getY(Hand.kLeft);
    // Inverting X values because we want positive values when we pull to the left.
    // Xbox controllers return positive values when you pull to the right by default.
    double strafe = -driverController.getX(Hand.kLeft); 
    double rotation = -driverController.getX(Hand.kRight); 

    //Limited rotation at slow speeds
    if(strafe < rotationLimiter || forward < rotationLimiter) {
      if(driverController.getBumper(Hand.kRight)) {
        rotation = rotation * 0.34;
      } else {
        //rotation = rotation * 0.2;
        rotation = Math.copySign((((Math.abs(rotation)-0.1)/0.9)*0.1)+0.1, rotation);
      }
    } else {
      rotation = rotation * 0.34;
    }

    // Inverting the bumper value because we want field-oriented drive by default.
    //boolean isFieldOriented = !driverController.getBumper(Hand.kLeft);    ////Original////
    boolean isFieldOriented = drivetrain.getFieldOriented();

    /*
    double speedLimit = .95;
    //if (operatorController.getTriggerAxis(Hand.kRight) > .5) {
      forward = forward * speedLimit;
      strafe = strafe * speedLimit;
   // } */

    //forward = Math.copySign(forward * forward, forward);
    //strafe = Math.copySign(strafe * strafe, strafe);

    forward = Utilities.deadband(forward, 0.04);
    strafe = Utilities.deadband(strafe, 0.04);
    rotation = Utilities.deadband(rotation, 0.04);


    if (Math.abs(rotation) > rotationDeadband) {
      lastRotation = rotation;
      stopRotating = false;
      i = 0;

    } else {
      // Checks to see if we were rotating in the previous iteration, but are not currently rotating 
      if (Math.abs(lastRotation) > rotationDeadband && Math.abs(rotation) <= rotationDeadband) {
        stopRotating = true;
      } 
      if(stopRotating == true) {
        if(i == 3) {
          operatorAngleAdjustment.setOffsetAngle(pigeon.getYawMod());
          rotation = 0;
          lastRotation = rotation;
          i = 0;
          stopRotating = false;
        } else {
          i++;
        }
      }


      // Checks to see if the Driver's button is being pressed, and sets the current offset angle
      if (operatorAngleAdjustment.getIsChangingGyroAngle()) {
        operatorAngleAdjustment.setOffsetAngle(operatorAngleAdjustment.getFutureOffsetAngle());
      }
      // Sets the error of the robot's angle offset & current gyro angle 
      error = operatorAngleAdjustment.getGyroAngleOffset() - pigeon.getYawMod();
      kP = forward == 0 && strafe == 0 ? stationaryP : movingP;
      if(error > 180) {
        error -= 360;
      } else if(error < -180) {
        error += 360;
      }
      rotation = operatorAngleAdjustment.calculateGyroOffset(error, kP);
      SmartDashboard.putNumber("Yaw Error", error);
      SmartDashboard.putNumber("Rotation", rotation);
    }


    if(operatorAngleAdjustment.getLimelightRotationMode()) {
      double tx = 0;
      if(vision.getPipeline() == 0 && shooter.getAvgRPM() > 250) {
        tx = -(Math.toRadians(vision.getDoubleValue("tx") - 2));
      } else if (vision.getPipeline() == 1) {
        tx = -(Math.toRadians(vision.getDoubleValue("tx")));
      }
      if(vision.getPipeline() == 1) {
        if(Math.abs(tx) <  Math.toRadians(2)) {
          rotation = (tx * Constants.Vision.VISIONCLOSEROTATIONP);
        } else if(Math.abs(tx) < Math.toRadians(5)) {
          rotation = (tx * Constants.Vision.VISIONMIDDLEROTATIONP);
        } else {
          rotation = (tx * Constants.Vision.VISIONOFFROTATIONP);
        }
      } else {
        if(Math.abs(tx) <  Math.toRadians(2)) {
          rotation = (tx * Constants.Vision.VISIONCLOSEROTATIONP);
        } else if(Math.abs(tx) < Math.toRadians(5)) {
          rotation = (tx * Constants.Vision.VISIONMIDDLEROTATIONP);
        } else {
          rotation = (tx * Constants.Vision.VISIONOFFROTATIONP);
        }
      }
    }

    if(operatorAngleAdjustment.getBallTrackingEnabled()){
      if(vision.pixyRightDigital.get()) {
        rotation = -(Math.toRadians(vision.getPixyRightValue() - 2) * Constants.BALLTRACKINGP);
      } else {
        rotation = 0;
      }
    }

    // Pass on joystick values to be calculated into angles and speeds
    drivetrain.calculateJoystickInput(forward, strafe, rotation, isFieldOriented);

    SmartDashboard.putNumber("Forward", forward);
    SmartDashboard.putNumber("Strafe", strafe);
    SmartDashboard.putNumber("Rotation", rotation);
    SmartDashboard.putBoolean("isFieldOriented", isFieldOriented);
  }

  @Override
  public void end(boolean interrupted) {
    // In the event this command stops, we don't want the motors to move
    drivetrain.stopAngleMotors();
    drivetrain.stopDriveMotors();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
