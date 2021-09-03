package frc.robot.commands.ShooterSystem;

import frc.robot.commands.Agitator.stopAgitator;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Stops the movement of balls in the robot
 * 
 * @author Nicholas Stokes
 */
public class feedSystemStop extends ParallelCommandGroup {
    private Agitator agitator;
    private Serializer serializer;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public feedSystemStop() {
        addCommands(
            new stopSerializer(serializer),
            new stopAgitator(agitator));
    }
}
