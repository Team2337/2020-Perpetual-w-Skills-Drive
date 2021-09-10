package frc.robot.commands.swerve;

import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Sets the robot's future angle offset. This should be on the <b>OPERATOR</b> joystick
 * @see OperatorAngleAdjustment
 * @author Bryce G., Madison J.
 * @category SWERVE
 */
public class SetGyroAngleOffset extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private String mode;
  private KickerWheel kickerWheel;
  private Shooter shooter;
  private SwerveDrivetrain swerveDrivetrain;
  private Vision vision;
  
  /**
   * Sets the robot's future angle offset. This should be on the <b>DRIVER</b> joystick
   * @param subsystem - OperatorAngleAdjustment Subsystem Object
   * @param mode - String value signifying the rotation mode the robot is in 
   */
  public SetGyroAngleOffset(OperatorAngleAdjustment operatorAngleAdjustment, String mode, Shooter shooter, Vision vision, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain) {
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.mode = mode;
    this.shooter = shooter;
    this.vision = vision;
    this.kickerWheel = kickerWheel;
    this.swerveDrivetrain = swerveDrivetrain;
    addRequirements(operatorAngleAdjustment);
  }

  @Override
  public void initialize() {
    operatorAngleAdjustment.setFutureOffsetAngle(mode, shooter, vision, kickerWheel, swerveDrivetrain);
  }

  @Override
  public void end(boolean interrupted) {
  }

}