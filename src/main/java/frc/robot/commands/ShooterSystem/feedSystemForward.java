package frc.robot.commands.ShooterSystem;

import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


/**
 * Move balls towards the shooter
 * 
 * @author Michael F., Bryce G.
 */
public class feedSystemForward extends SequentialCommandGroup {
    //private Agitator agitator;
    //private Serializer serializer;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public feedSystemForward(Agitator agitator, Serializer serializer) {
        addCommands(
            //new holdKickerPosition(Robot.KickerWheel), //TODO: FIX ME
            new runAgitator(agitator, Constants.AGITATORSPEED),
            new runSerializer(serializer, Constants.SERIALIZERDRIVERFORWARDSPEED));
    }
}
