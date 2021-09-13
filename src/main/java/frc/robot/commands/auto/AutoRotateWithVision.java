package frc.robot.commands.auto;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets the forwards value to a set a mock joystick value
 * @see SwerveDrivetrain
 * @author Bryce G.
 * @category SWERVE
 */
public class AutoRotateWithVision extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SwerveDrivetrain swerveDrivetrain;
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private final Pigeon pigeon;
  private final Vision vision;
  private final Shooter shooter;

  private double rotation;
  private double endAngleDegree;

  private int pipeline;

  /**
   * Sets the forwards value to a set a mock joystick value
   * 
   * @param subsystem - SwerveDrivetrain Subsystem object
   * @param forward   - mock forward joystick value
   */
  public AutoRotateWithVision(SwerveDrivetrain swerveDrivetrain, int pipeline, OperatorAngleAdjustment operatorAngleAdjustment, Shooter shooter, Vision vision, Pigeon pigeon) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.vision = vision;
    this.shooter = shooter;
    this.pigeon = pigeon;
    this.pipeline = pipeline;
    addRequirements(swerveDrivetrain);
  }
  
  @Override
  public void initialize() {
    operatorAngleAdjustment.setLimelightRotationMode(true);
  }

  @Override
  public void execute() {
    double tx = 0;
      if(vision.getPipeline() == 0 && shooter.getAvgRPM() > 250) {
        tx = -(Math.toRadians(vision.getDoubleValue("tx") - 3));
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
    
    // Pass on joystick values to be calculated into angles and speeds
    swerveDrivetrain.calculateJoystickInput(0, 0, rotation, true);
  }
  
  @Override
  public void end(boolean interrupted) {
    operatorAngleAdjustment.setLimelightRotationMode(false);
    operatorAngleAdjustment.setOffsetAngle(-Robot.Utilities.getPigeonYawMod(pigeon));
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
