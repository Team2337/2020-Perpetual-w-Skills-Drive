package frc.robot.commands.auto.commandgroups.common.systemactions;

import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.intake.runIntake;
import frc.robot.commands.Serializer.runSerializer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class IntakeAndFireFromPartners extends SequentialCommandGroup {
    private final Agitator m_agitator;
    private final Intake m_intake;
    private final Serializer m_serializer;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public IntakeAndFireFromPartners(double timeout,  Intake intake, Agitator agitator, Serializer serializer) {
        m_agitator = agitator;
        m_intake = intake;
        m_serializer = serializer;
        addCommands(
            new WaitCommand(0.5),
            new runIntake(m_intake, Constants.INTAKEFORWARDSPEED), 
            new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED),
            new runAgitator(m_agitator, Constants.AGITATORSPEED),
            new WaitCommand(timeout).withTimeout(timeout)
        );
    }
}