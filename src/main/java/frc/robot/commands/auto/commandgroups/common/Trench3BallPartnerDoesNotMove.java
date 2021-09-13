package frc.robot.commands.auto.commandgroups.common;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.commands.intake.runIntake;
import frc.robot.commands.auto.*;
import frc.robot.commands.auto.commandgroups.common.movement.InTrench3Ball;
import frc.robot.commands.auto.commandgroups.common.movement.PostTrench6Ball;
import frc.robot.commands.auto.commandgroups.common.movement.PreTrenchPartnerOnRight;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class Trench3BallPartnerDoesNotMove extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  public double intakeSpeed = 0.6;
  public double driveDistance = 110;
  public double speed = -0.45;

  /**
   * Drives from the initiation line to the generator command group
   */
  public Trench3BallPartnerDoesNotMove(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
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
      new resetDriveEncoders(m_swerveDrivetrain),
      new PreTrenchPartnerOnRight(m_operatorAngleAdjustment, m_swerveDrivetrain, m_pigeon, m_vision),
      new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
      new InTrench3Ball(driveDistance, speed, m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel, m_swerveDrivetrain, m_pigeon, m_vision),
      new PostTrench6Ball(m_operatorAngleAdjustment, m_serializer, m_shooter, m_swerveDrivetrain, m_pigeon, m_vision)
    ); 
  }
}
