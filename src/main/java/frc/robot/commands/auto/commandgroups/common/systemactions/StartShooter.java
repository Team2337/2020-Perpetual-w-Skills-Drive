package frc.robot.commands.auto.commandgroups.common.systemactions;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.Shooter.startShooter;

/**
 * Runs the kicker and starts the shooter command group
 * @author Madison J. 
 * @category AUTON 
 */
public class StartShooter extends ParallelCommandGroup {
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

 /**
 * Runs the kicker and starts the shooter command group
 */
  public StartShooter(Shooter shooter, KickerWheel kickerWheel) {
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    addCommands(
      new runKicker(m_kickerWheel ),
      new startShooter(m_shooter)
    );
  
  }

 
}
