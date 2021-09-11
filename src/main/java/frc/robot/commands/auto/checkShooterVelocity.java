package frc.robot.commands.auto;

import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets the module angles to the desired rotation angle and rotates the robot a specified direction, either left or right
 * @author Madison J.
 * @category AUTON
 */
public class checkShooterVelocity extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Serializer m_subsystem;
    private final KickerWheel kickerWheel;
    private final Shooter shooter;
    private double shooterVelocity;
    private double kickerVelocity;
    private boolean finished;
    private boolean isAtVelocity;

  public checkShooterVelocity(Serializer subsystem, double shooterVelocity, double kickerVelocity, KickerWheel kickerWheel, Shooter shooter) {
    m_subsystem = subsystem;
    this.kickerWheel = kickerWheel;
    this.shooter = shooter;
    addRequirements(subsystem);
    this.shooterVelocity = shooterVelocity;
    this.kickerVelocity = kickerVelocity;
  }

  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() {
    if ((shooter.getAvgRPM() > (shooterVelocity * 0.75)) && (kickerWheel.getKickerSpeed() > (kickerVelocity * 0.75))) {
      isAtVelocity = true;
    } else {
      isAtVelocity = false;
    }
    finished = isAtVelocity ? true: false;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
}