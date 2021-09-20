package frc.robot.commands.auto.commandgroups.common.systemactions;

import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.commands.Serializer.runSerializer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class FirePartnerBalls extends SequentialCommandGroup {

    private final Agitator m_agitator;
    private final Intake m_intake;
    private final Serializer m_serializer;
    private final OperatorAngleAdjustment m_operatorAngleAdjustment;
    private final KickerWheel m_kickerWheel;
    private final Shooter m_shooter;
    
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public FirePartnerBalls(int numOfPartners, Intake intake, Agitator agitator, Serializer serializer, OperatorAngleAdjustment operatorAngleAdjustment, Shooter shooter, KickerWheel kickerWheel
    ) {
        this.m_agitator = agitator;
        this.m_intake = intake;
        this.m_serializer = serializer;
        m_shooter = shooter;
        m_kickerWheel = kickerWheel;
        m_operatorAngleAdjustment = operatorAngleAdjustment;

        addCommands(
                new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
                new runAgitator(m_agitator, Constants.AGITATORSPEED),
                new FirePreloads(m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel).withTimeout(3.2),
                new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED).withTimeout(8) //9.5
                );
        
    }
}