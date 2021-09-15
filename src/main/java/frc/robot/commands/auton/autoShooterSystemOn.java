package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.autoStartShooter;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;

/**
 * Nine ball auto
 * Runs the shooter for 1.5 seconds before intaking balls from our partners
 * 
 * @author Bryce G.
 */
public class autoShooterSystemOn extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    public autoShooterSystemOn(KickerWheel kickerWheel, Shooter shooter, OperatorAngleAdjustment operatorAngleAdjustment, Serializer serializer, Agitator agitator, Intake intake) {
        addCommands(
            new runKicker(kickerWheel),
            new autoStartShooter(shooter, Constants.SHOOTSPEEDCLOSE),
            new WaitCommand(0.2).withTimeout(0.2), 
            new autoResetRampRate(operatorAngleAdjustment, shooter),
            new WaitCommand(1.3).withTimeout(1.3), 
            new runSerializer(serializer, Constants.SERIALIZERDRIVERFORWARDSPEED), 
            new runAgitator(agitator, Constants.AGITATORSPEED),
            new WaitCommand(1.5).withTimeout(1.5), 
            new runIntake(intake, Constants.INTAKEFORWARDSPEED), 
            new WaitCommand(10).withTimeout(10)
        );
    }
}
