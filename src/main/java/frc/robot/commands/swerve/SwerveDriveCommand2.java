package frc.robot.commands.swerve;

import frc.robot.Constants;
import frc.robot.Utilities;
import frc.robot.commands.Vision.switchDriverCam;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.*;

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

  /**
   * Command running the swerve calculations with the joystick
   *
   * @param subsystem - SwerveDrivetrain subsystem object
   */
  public SwerveDriveCommand2(SwerveDrivetrain drivetrain, XboxController driverController, XboxController operatorController, Shooter shooter, OperatorAngleAdjustment operatorAngleAdjustment, Vision vision) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

    this.driverController = driverController;
    this.operatorController = operatorController;
    this.shooter = shooter;
    this.vision = vision;
    this. operatorAngleAdjustment = operatorAngleAdjustment;
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
    double rotation = -driverController.getX(Hand.kRight) * 0.34;
    // Inverting the bumper value because we want field-oriented drive by default.
    //boolean isFieldOriented = !driverController.getBumper(Hand.kLeft);    ////Original////
    boolean isFieldOriented = drivetrain.getFieldOriented();


    double speedLimit = .95;
    //if (operatorController.getTriggerAxis(Hand.kRight) > .5) {
      forward = forward * speedLimit;
      strafe = strafe * speedLimit;
   // }

    //forward = Math.copySign(forward * forward, forward);
    //strafe = Math.copySign(strafe * strafe, strafe);

    forward = Utilities.deadband(forward, 0.04);
    strafe = Utilities.deadband(strafe, 0.04);
    rotation = Utilities.deadband(rotation, 0.04);

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
