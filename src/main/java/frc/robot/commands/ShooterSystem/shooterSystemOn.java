package frc.robot.commands.ShooterSystem;

import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.Shooter.startShooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Turns the shooter and kicker on to their designated velocities, as decided by
 * the mode that is queued by the operator
 * @see OperatorAngleAdjustment
 * 
 * @author Michael F., Bryce G. 
 */
public class shooterSystemOn extends SequentialCommandGroup {
    private Shooter shooter;
    private KickerWheel kickerWheel;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public shooterSystemOn() {
        addCommands(
            new WaitCommand(0.1).withTimeout(0.1),
            new runKicker(kickerWheel),
            new startShooter(shooter));
    }
}
