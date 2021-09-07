package frc.robot.commands.Serializer;

import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that sets the kicker speed using the Kicker subsystem.
 */
public class dirverserializerBallControl extends CommandBase {
    private Agitator agitator;
    private Serializer serializer;
    private Shooter shooter;


    /**
     * Sets the kicker's speed.
     * 
     * @param kickerWheel The subsystem used by this command. (Kicker)
     */
    public dirverserializerBallControl(Serializer serializer, Agitator agitator) {
        this.serializer = serializer;
        this.agitator = agitator;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(serializer);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

          
        if (shooter.shooterAtVelocity) { 
    
                    serializer.setSerializerSpeed(Constants.SERIALIZERDRIVERFORWARDSPEED);
                    agitator.setAgitatorSpeed(Constants.AGITATORSHOOTSPEED);


            
        } else {
            // If no-one is trying to control the kicker wheel, stop it
            serializer.stopSerializer();
            agitator.stopAgitator();

        }
   
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
}
