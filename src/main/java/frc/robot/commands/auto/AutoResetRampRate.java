package frc.robot.commands.auto;

import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Resets the ramp rate on the shooter to be zero
 * @author Bryce G.
 */
public class AutoResetRampRate extends InstantCommand {

  private final OperatorAngleAdjustment subsystem;
  private final Shooter shooter;

  /**
   * Resets the ramp rate on the shooter to be zero
   * 
   * @param m_subsystem OperatorAngleAdjustment Subsystem object
   */
  public AutoResetRampRate(OperatorAngleAdjustment m_subsystem, Shooter shooter) {
    //Puts the parameters in the command's variables to be used around as a shortcut.
    subsystem = m_subsystem;
    this.shooter = shooter;
    addRequirements(m_subsystem);
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