package frc.robot.commands.swerve;

import frc.robot.Robot;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Sets the slow rotation mode and speed for slow rotation. 
 * The rotational changes are applied in SwerveDriveCommand
 * @see SwerveDriveCommand
 * @author Bryce G.
 * @category SWERVE
 */
public class slowRotateRight extends CommandBase {
  private SwerveDrivetrain swerveDrivetrain;
  private OperatorAngleAdjustment operatorAngleAdjustment;
  private Pigeon pigeon;
  public int i;

  /**
   * Sets the slow rotation mode and speed for slow rotation. 
   * The rotational changes are applied in SwerveDriveCommand
   * @param subsystem
   * @param slowRotateMode
   * @param slowRotateSpeed
   * @see SwerveDriveCommand
   */
  public slowRotateRight(SwerveDrivetrain swerveDrivetrain, OperatorAngleAdjustment operatorAngleAdjustment, Pigeon pigeon) {
    this.swerveDrivetrain = swerveDrivetrain;
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.pigeon = pigeon;
  }

  @Override
  public void initialize() {
    i = 0;
  }

  @Override
  public void execute() {
    if(i < 2) {
      swerveDrivetrain.calculateJoystickInput(0, 0, -0.1, true);
    }
    i++;
  }

  @Override
  public boolean isFinished() {
    return i > 6;
  }

  @Override
  public void end(boolean interrupted) {
    swerveDrivetrain.calculateJoystickInput(0, 0, 0, true);
    operatorAngleAdjustment.setOffsetAngle(pigeon.getYawMod());
  }
}
