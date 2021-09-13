package frc.robot.commands.auto.commandgroups.common.systemactions;

import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.Intake;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.autoBallCounter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;;


/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class FireOnePartnerBall extends SequentialCommandGroup {
    private final Agitator m_agitator;
    private final Intake m_intake;
    private final Serializer m_serializer;
    private final OperatorAngleAdjustment m_operatorAngleAdjustment;
    private final KickerWheel m_kickerWheel;
    private final Shooter m_shooter;

    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public FireOnePartnerBall(int numOfPartners, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, Intake intake) {

        m_operatorAngleAdjustment = operatorAngleAdjustment;
        m_agitator = agitator;
        m_intake = intake;
        m_serializer = serializer;
        m_shooter = shooter;
        m_kickerWheel = kickerWheel;
        
        addCommands(
                new FirePreloads(m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel).withTimeout(2.75), 
                new runAgitator(m_agitator, Constants.AGITATORSPEED),
                new ParallelRaceGroup(
                    new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED).withTimeout(6), //3.5 TODO: Fix MEEEEEEEEEEEEEEEEEEEEEEEEEEE
                    new autoBallCounter(m_operatorAngleAdjustment, m_serializer.middleSerializerSensor, 5, 3)
                ),
                new WaitCommand(0.4).withTimeout(0.4)
            );
        
    }
}