package frc.robot.commands.auto;

import frc.robot.Robot;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Shoots the ball
 * @author Michael Francis
 */
public class autoShooterAtSpeed extends CommandBase {
  
  private final OperatorAngleAdjustment subsystem;
  private final Shooter shooter;

  /**
   * Shoots the ball at a specified speed.
   * @param m_subsystem
   * The subsystem that the command uses (Shooter)
   */
  public autoShooterAtSpeed(OperatorAngleAdjustment m_subsystem, Shooter shooter) {
    //Puts the parameters in the command's variables to be used around as a shortcut.
    subsystem = m_subsystem;
    this.shooter = shooter;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return shooter.shooterAtVelocity;
  }

}
