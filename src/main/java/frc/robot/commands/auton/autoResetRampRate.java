package frc.robot.commands.auton;

import frc.robot.Robot;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Resets the ramp rate on the shooter to be zero
 * @author Bryce G.
 */
public class autoResetRampRate extends InstantCommand {

  private final OperatorAngleAdjustment OperatorAngleAdjustment;
  private final Shooter shooter;

  /**
   * Resets the ramp rate on the shooter to be zero
   * 
   * @param m_subsystem OperatorAngleAdjustment Subsystem object
   */
  public autoResetRampRate(OperatorAngleAdjustment operatorAngleAdjustment, Shooter shooter) {
    //Puts the parameters in the command's variables to be used around as a shortcut.
    this.OperatorAngleAdjustment = operatorAngleAdjustment;
    this.shooter = shooter;

    addRequirements(operatorAngleAdjustment);
  }

  @Override
  public void initialize() {
    //Sets the ramp rate on each motor to zero
    shooter.leftShootMotor.configClosedloopRamp(0);
    shooter.rightShootMotor.configClosedloopRamp(0);
  }

  @Override
  public void end(boolean interrupted) {
  }
}
