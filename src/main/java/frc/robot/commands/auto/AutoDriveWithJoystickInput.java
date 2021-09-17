package frc.robot.commands.auto;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.SwerveDrivetrain;
//import edu.wpi.first.hal.sim.ConstBufferCallback;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets the forwards value to a set a mock joystick value
 * @see SwerveDrivetrain
 * @author Bryce G.
 * @category SWERVE
 */
public class AutoDriveWithJoystickInput extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SwerveDrivetrain swerveDrivetrain;
  private final Pigeon pigeon;
  private final OperatorAngleAdjustment operatorAngleAdjustment;

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

  /**
   * Sets the forwards value to a set a mock joystick value
   * 
   * @param subsystem - SwerveDrivetrain Subsystem object
   * @param forward   - mock forward joystick value
   */
  public AutoDriveWithJoystickInput(SwerveDrivetrain swerveDrivetrain, double encoderDist, double forwardDist, double horizontalDist, double endAngleDegree, Pigeon pigeon, OperatorAngleAdjustment operatorAngleAdjustment) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.pigeon = pigeon;
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.encoderDist = encoderDist;
    this.forwardDist = forwardDist;
    this.strafe = -horizontalDist * Constants.Auton.INCHESTOJOYSTICKVALUE;
    this.forward = forwardDist * Constants.Auton.INCHESTOJOYSTICKVALUE;
    this.endAngleDegree = endAngleDegree;
    addRequirements(swerveDrivetrain);
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
   
   // Pass on joystick values to be calculated into angles and speeds
   swerveDrivetrain.calculateJoystickInput(forward, strafe, rotation, true);
  }

  @Override
  public void end(boolean interrupted) {
    operatorAngleAdjustment.setOffsetAngle(Robot.Utilities.getPigeonYawMod(pigeon));
    //System.out.println("Encoder Ticks: " + SwerveDrivetrain.getModule(3).getDriveEncoderValue());
    swerveDrivetrain.zeroAllDriveEncoders();


  }

  @Override
  public boolean isFinished() {
    return (Math.abs(swerveDrivetrain.getModulePosition(3)) > encoderDist * Constants.Swerve.TICKSPERINCH);
  }
}
