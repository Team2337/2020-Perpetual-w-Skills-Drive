package frc.robot.commands.auto.commandgroups.common.systemactions;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Serializer;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.autoBallCounter;

/**
 * Runs the serializer, agitator, and the intake command group
 * @author Madison J. 
 * @category AUTON 
 */
public class FeedShooter extends ParallelCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;

 /**
 * Runs the serializer, agitator, and the intake command group
 */
  public FeedShooter(int ballsShot, OperatorAngleAdjustment operatorAngleAdjustment, Intake intake, Agitator agitator, Serializer serializer) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    parallel(
    new autoBallCounter(m_operatorAngleAdjustment, new DigitalInput(0), 10, ballsShot),
    new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED),
    new runAgitator(m_agitator, Constants.AGITATORSPEED),
    new runIntake(m_intake, Constants.INTAKEFORWARDSPEED)
    );  
  }

 
}
