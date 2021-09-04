package frc.robot.commands.Serializer;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that sets the kicker speed using the Kicker subsystem.
 */
public class serializerBallControl extends CommandBase {
    private Agitator agitator;
    private RobotContainer robotContainer;
    private Serializer serializer;
    private Shooter shooter;
    private int i = 0;
    private int iteration = 0;
    private double position = 0;

    /**
     * Sets the kicker's speed.
     * 
     * @param kickerWheel The subsystem used by this command. (Kicker)
     */
    public serializerBallControl(Serializer serializer) {
        this.serializer = serializer;
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
        if (robotContainer.driverController.getTriggerAxis(Hand.kRight) > .5) { 
            if (shooter.shooterAtVelocity) { 
                /* if (i < 10) {
                    if(Robot.KickerWheel.getKickerSpeed() < 3500){
                        Robot.Serializer.setSerializerSpeed(Constants.SERIALIZERREVERSESPEED);
                    }
                } else if (i == 10){
                    Robot.Serializer.stopSerializer();
                } else if (i > 50 * 0.5) { */
                    serializer.setSerializerSpeed(Constants.SERIALIZERDRIVERFORWARDSPEED);
                    agitator.setAgitatorSpeed(Constants.AGITATORSHOOTSPEED);
                /* }
                i++; */
            }
            // If the driver isn't attempting to control it and the operator is
        } else if (robotContainer.operatorController.getTriggerAxis(Hand.kLeft) > .5) {
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
            //Also, the speed checking iterations would need to be reset
            i = 0;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
}
