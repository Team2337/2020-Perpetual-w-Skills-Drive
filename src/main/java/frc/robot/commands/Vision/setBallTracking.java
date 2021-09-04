package frc.robot.commands.Vision;

//import frc.robot.subsystems.OperatorAngleAdjustment;// added vision for op angle adj
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Sets whether or not we're tracking a ball
 */
public class setBallTracking extends InstantCommand {
    //private OperatorAngleAdjustment operatorAngleAdjustment;
    private Vision vision; // added op angle adj
    private boolean ballTrackingEnabled;

    /**
     * Sets whether or not we're tracking a ball
     * 
     * @param operatorAngleAdjustment The OperatorAngleAdjustment subsystem
     * @param ballTrackingEnabled     Whether or not we're tracking a ball
     */
    //public setBallTracking(OperatorAngleAdjustment operatorAngleAdjustment, boolean ballTrackingEnabled) {
       // this.operatorAngleAdjustment = operatorAngleAdjustment;
       public setBallTracking(Vision vision, boolean ballTrackingEnabled) {// added vision for op angle adj
        this.ballTrackingEnabled = ballTrackingEnabled;
        //addRequirements(operatorAngleAdjustment);
        addRequirements(vision);  // added vision for op angle adj
    }

    @Override
    public void initialize() {
        //operatorAngleAdjustment.setBallTrackingEnabled(ballTrackingEnabled);
    }
}