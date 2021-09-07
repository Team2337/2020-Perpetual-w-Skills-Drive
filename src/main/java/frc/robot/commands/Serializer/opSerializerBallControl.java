package frc.robot.commands.Serializer;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that sets the kicker speed using the Kicker subsystem.
 */
public class opSerializerBallControl extends CommandBase {
    private Agitator agitator;
    private RobotContainer robotContainer;
    private Serializer serializer;
    private int iteration = 0;
    private double position = 0;

    /**
     * Sets the kicker's speed.
     * 
     * @param kickerWheel The subsystem used by this command. (Kicker)
     */
    public opSerializerBallControl(Serializer serializer, Agitator agitator) {
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

    
    
        // The driver takes priority
         if (robotContainer.getOpTriggerLeft()) {
            agitator.setAgitatorSpeed(Constants.AGITATORSPEED);
            if(serializer.bottomSerializerSensor.get() && !serializer.topSerializerSensor.get()) {
                if(iteration > 5 && iteration < 9) { 
                    position = serializer.getSerializerPosition() + 7400;
                    serializer.setPosition(position);
                }
                iteration++;

                if((Robot.Utilities.withinTolerance(position, serializer.getSerializerPosition(), 100)  && iteration > 39)
                || (iteration > 40) ||( !serializer.bottomSerializerSensor.get())
                ) {
                    iteration = 0;
                }
            } else if(serializer.topSerializerSensor.get() || !serializer.bottomSerializerSensor.get()) {
                serializer.stopSerializer(); 
            }  
            if(serializer.topSerializerSensor.get()){
                agitator.stopAgitator();
            }
            
        } else {
            // If no-one is trying to control the kicker wheel, stop it
            serializer.stopSerializer();
            agitator.stopAgitator();

        }
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        serializer.stopSerializer();
        agitator.stopAgitator();
    }
}
