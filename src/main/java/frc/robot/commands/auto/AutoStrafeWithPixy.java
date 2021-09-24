package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;

/**
 * Sets the forwards value to a set a mock joystick value
 * @see SwerveDrivetrain
 * @author Bryce G.
 * @category SWERVE
 */
public class AutoStrafeWithPixy extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SwerveDrivetrain SwerveDrivetrain;

  private final Vision vision;
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private final Pigeon pigeon;

  private double forward;
  private double strafe;
  private double rotation;

  private double forwardDist;

  private double endAngleDegree;
  private double currentGyro;
  private double rotationError;

  private double rotationP = 0.009; 
  private double maxRotationSpeed = 0.15;
  private double encoderDist = 0;
  private double offset;

  /**
   * Sets the forwards value to a set a mock joystick value
   * 
   * @param subsystem - SwerveDrivetrain Subsystem object
   * @param forward   - mock forward joystick value
   */
  public AutoStrafeWithPixy(SwerveDrivetrain SwerveDrivetrain, double encoderDist, double forwardDist,
     double endAngleDegree, Vision vision, OperatorAngleAdjustment operatorAngleAdjustment, Pigeon pigeon) {
    this.SwerveDrivetrain = SwerveDrivetrain;
    this.vision = vision;
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.pigeon = pigeon;
    this.encoderDist = encoderDist;
    this.forwardDist = forwardDist;
    this.forward = forwardDist * Constants.Auton.INCHESTOJOYSTICKVALUE;
    this.endAngleDegree = endAngleDegree;
    addRequirements(SwerveDrivetrain);
  }
  
  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() {
    currentGyro = Robot.Utilities.getPigeonYawMod(pigeon);
    rotationError = (endAngleDegree - currentGyro);
    rotation = rotationError * rotationP;
    rotation = rotation > maxRotationSpeed ? maxRotationSpeed : rotation;
    
    if(vision.getPixyRightTarget()) {
      strafe = (Math.toRadians(vision.getPixyRightValue() - 2) * Constants.Auton.AUTOSTRAFEP);
    } else {
      strafe = 0;
    } 
    SmartDashboard.putNumber("pixy strafe", strafe);
   // Pass on joystick values to be calculated into angles and speeds
   SwerveDrivetrain.calculateJoystickInput(forward, strafe, rotation, true);

  }

  @Override
  public void end(boolean interrupted) {
    
    operatorAngleAdjustment.setOffsetAngle(Robot.Utilities.getPigeonYawMod(pigeon));
    System.out.println("Encoder Ticks: " + SwerveDrivetrain.getModulePosition(3));
    SwerveDrivetrain.zeroAllDriveEncoders();


  }

  @Override
  public boolean isFinished() {
    
    return (Math.abs(SwerveDrivetrain.getModulePosition(3)) > encoderDist * Constants.Swerve.TICKSPERINCH) 
    ||( ((Math.abs(SwerveDrivetrain.getModulePosition(3)) > (encoderDist * Constants.Swerve.TICKSPERINCH) * 0.95))); 
    //&& vision.getPixyRightTarget());
  }
}
