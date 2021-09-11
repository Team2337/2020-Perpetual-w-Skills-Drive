package frc.robot.commands.auto.commandgroups.common.systemactions;

import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.commands.Serializer.runSerializer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class FirePartnerBalls extends SequentialCommandGroup {
    private final Intake intake;
    private final Agitator agitator;
    private final Serializer seriallizer;

    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public FirePartnerBalls(int numOfPartners, Intake intake, Agitator agitator, Serializer serializer) {
        this.agitator = agitator;
        this.intake = intake;
        this.seriallizer = seriallizer;

        addCommands(
                new runIntake(intake, Constants.INTAKEFORWARDSPEED),
                new runAgitator(agitator, Constants.AGITATORSPEED),
                new FirePreloads().withTimeout(3.2),
                new runSerializer(serializer, Constants.SERIALIZERDRIVERFORWARDSPEED).withTimeout(9.5) //10
                );
        
    }
}