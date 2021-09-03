package frc.robot.commands.ShooterSystem;

import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Moves balls downwards, away from the shooter
 * 
 * @author Sean Lynch
 */
public class feedSystemReverse extends ParallelCommandGroup {
    private Agitator agitator;
    private Serializer serializer;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public feedSystemReverse() {
        addCommands(
            new runSerializer(serializer, Constants.SERIALIZERREVERSESPEED),
            new runAgitator(agitator, Constants.AGITATORREVERSESPEED));
    }
}
