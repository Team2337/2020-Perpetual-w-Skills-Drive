package frc.robot.commands.auto.commandgroups.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.commands.Shooter.stopShooter;
import frc.robot.commands.auto.checkShooterVelocity;
import frc.robot.commands.auto.commandgroups.common.systemactions.*;

/**
 * Starts the shooter and when when the velocity of the shooter is reached run feed shooter until 3 balls have been shot then turn off the shooter command group
 * @author Madison J. 
 * @category AUTON 
 */
public class ShootThreeBall extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

 /**
 * Starts the shooter and when when the velocity of the shooter is reached run feed shooter until 3 balls have been shot then turn off the shooter command group
 */
  public ShootThreeBall(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    addCommands(
     new StartShooter(m_shooter, m_kickerWheel),
     new checkShooterVelocity(m_serializer, Constants.SHOOTSPEEDCLOSE, Constants.KICKERSPEEDCLOSE, m_kickerWheel, m_shooter).withTimeout(3),
     new FeedShooter(3, m_operatorAngleAdjustment, m_intake, m_agitator, m_serializer).withTimeout(5),
     new stopShooter(m_shooter)
    );
  
  }

 
}
