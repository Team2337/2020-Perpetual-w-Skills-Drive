package frc.robot.commands.auto.commandgroups.common.systemactions;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
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
  private final OperatorAngleAdjustment operatorAngleAdjustment;
  private final Intake intake;
  private final Agitator agitator;
  private final Serializer serializer;

 /**
 * Runs the serializer, agitator, and the intake command group
 */
  public FeedShooter(int ballsShot, OperatorAngleAdjustment operatorAngleAdjustment, Intake intake, Agitator agitator, Serializer serializer) {
    this.operatorAngleAdjustment = operatorAngleAdjustment;
    this.agitator = agitator;
    this.intake = intake;
    this.serializer = serializer;
    parallel(
    new autoBallCounter(operatorAngleAdjustment, new DigitalInput(0), 10, ballsShot),
    new runSerializer(serializer, Constants.SERIALIZERDRIVERFORWARDSPEED),
    new runAgitator(agitator, Constants.AGITATORSPEED),
    new runIntake(intake, Constants.INTAKEFORWARDSPEED)
    );  
  }

 
}
