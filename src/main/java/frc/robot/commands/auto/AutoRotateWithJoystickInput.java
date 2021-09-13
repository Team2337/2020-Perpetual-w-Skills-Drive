package frc.robot.commands.auto;

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
public class AutoRotateWithJoystickInput extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SwerveDrivetrain swerveDrivetrain;
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private final Pigeon pigeon;

  private double rotation;

  private double endAngleDegree;
  private double currentGyro;
  private double rotationError;

  private double rotationP = 0.01; 
  private double maxRotationSpeed = 0.2;

  /**
   * Sets the forwards value to a set a mock joystick value
   * 
   * @param subsystem - SwerveDrivetrain Subsystem object
   * @param forward   - mock forward joystick value
   */
  public AutoRotateWithJoystickInput(SwerveDrivetrain swerveDrivetrain, double endAngleDegree, OperatorAngleAdjustment operatorAngleAdjustment, Pigeon pigeon) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.pigeon = pigeon;
    this.endAngleDegree = endAngleDegree;
    addRequirements(swerveDrivetrain);
  }
  
  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    currentGyro = -Robot.Utilities.getPigeonYawMod(pigeon);
    rotationError = (endAngleDegree - currentGyro);
    rotation = rotationError * rotationP;
    rotation = rotation > maxRotationSpeed ? maxRotationSpeed : rotation;
   
   // Pass on joystick values to be calculated into angles and speeds
   swerveDrivetrain.calculateJoystickInput(0, 0, rotation, true);
  }

  @Override
  public void end(boolean interrupted) {
    operatorAngleAdjustment.setOffsetAngle(-Robot.Utilities.getPigeonYawMod(pigeon));

  }

  @Override
  public boolean isFinished() {
    return Robot.Utilities.withinTolerance(endAngleDegree, -Robot.Utilities.getPigeonYawMod(pigeon), 2);
  }
}
