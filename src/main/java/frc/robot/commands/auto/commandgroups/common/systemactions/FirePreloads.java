package frc.robot.commands.auto.commandgroups.common.systemactions;

import frc.robot.Constants;


import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.intake.runIntake;
import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.AutoResetRampRate;
import frc.robot.commands.auto.autoShooterAtSpeed;
import frc.robot.commands.auto.autoStartShooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class FirePreloads extends SequentialCommandGroup {
    private final Agitator m_agitator;
    private final Intake m_intake;
    private final Serializer m_serializer;
    private final OperatorAngleAdjustment m_operatorAngleAdjustment;
    private final KickerWheel m_kickerWheel;
    private final Shooter m_shooter;

    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public FirePreloads(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel) {
        m_operatorAngleAdjustment = operatorAngleAdjustment;
        m_agitator = agitator;
        m_intake = intake;
        m_serializer = serializer;
        m_shooter = shooter;
        m_kickerWheel = kickerWheel;

        addCommands(
            new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
            new runKicker(m_kickerWheel),
            new autoStartShooter(m_shooter, Constants.SHOOTSPEEDCLOSE),
            new WaitCommand(0.2).withTimeout(0.2), 
            new AutoResetRampRate(m_operatorAngleAdjustment, m_shooter).withTimeout(0.1),
            new autoShooterAtSpeed(m_operatorAngleAdjustment, m_shooter),
            new runAgitator(m_agitator, Constants.AGITATORSPEED),
            new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED).withTimeout(3)
        );
    }
}