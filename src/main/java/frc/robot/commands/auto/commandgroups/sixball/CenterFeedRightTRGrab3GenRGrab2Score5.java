package frc.robot.commands.auto.commandgroups.sixball;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.commands.auto.commandgroups.common.Trench3BallPartnerDoesNotMove;
import frc.robot.commands.auto.commandgroups.common.movement.PostTrench;
import frc.robot.commands.auto.commandgroups.common.systemactions.FirePartnerBalls;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;

/**
 * Shoot 6 balls with a partner feeding on our right, intake 3 balls from the trench and 2 from the generator, then shoot 5 balls
 */
public class CenterFeedRightTRGrab3GenRGrab2Score5 extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
 /**
 * Shoots 9 balls centered on the goal then the chassis drives to the generator and we intake 3 balls  
 */
  public CenterFeedRightTRGrab3GenRGrab2Score5(double delay,Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;
    addCommands(
      new WaitCommand(delay).withTimeout(delay),
      new FirePartnerBalls(1, m_intake, m_agitator, m_serializer, m_operatorAngleAdjustment, m_shooter, m_kickerWheel),
      new stopSerializer(m_serializer),
      new Trench3BallPartnerDoesNotMove(m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel, m_swerveDrivetrain, m_pigeon, m_vision), 
      new PostTrench(m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel, m_swerveDrivetrain, m_pigeon, m_vision)
    );
  
  }

 
}
