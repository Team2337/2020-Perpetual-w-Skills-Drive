package frc.robot.commands.Serializer;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command that sets the kicker speed using the Kicker subsystem.
 */
public class serializerBallControl extends CommandBase {
    private Agitator agitator;
    public Serializer serializer;
    private Shooter shooter;
    private XboxController driveController, operatorController;
    private int i = 0;
    private int iteration = 0;
    private double position = 0;

    /**
     * Sets the kicker's speed.
     * 
     * @param kickerWheel The subsystem used by this command. (Kicker)
     */
    public serializerBallControl(Serializer serializer, Agitator agitator, Shooter shooter, XboxController driveController, XboxController operatorController)  {// , Shooter shooter, Agitator agitator) {
        this.serializer = serializer;
        this.driveController = driveController;
        this.operatorController = operatorController;
        this.agitator = agitator;
        this.shooter = shooter;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(serializer); //, shooter, agitator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {



        if (driveController.getRawAxis(3) > .5) { //Driver joystick right trigger
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
        } else if (operatorController.getRawAxis(2) > .5) { //Operator joystick left trigger
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
