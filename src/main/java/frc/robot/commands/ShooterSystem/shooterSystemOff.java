package frc.robot.commands.ShooterSystem;

import frc.robot.commands.KickerWheel.holdKickerPosition;
import frc.robot.commands.KickerWheel.stopKicker;
import frc.robot.commands.Shooter.stopShooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Stop the shooter system
 * 
 * @author Sean Lynch
 */
public class shooterSystemOff extends ParallelCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    
    public shooterSystemOff(Shooter shooter, KickerWheel kickerWheel) {
        addCommands(
            new stopShooter(shooter),
            new stopKicker(kickerWheel).andThen(new holdKickerPosition(kickerWheel)));
    }
}
