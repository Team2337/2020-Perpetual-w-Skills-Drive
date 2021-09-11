package frc.robot.commands.auto.commandgroups.nineball;

import frc.robot.commands.auto.AutoDriveWithJoystickInput;
import frc.robot.commands.auto.zeroDriveEncoders;
import frc.robot.commands.auto.commandgroups.common.systemactions.FirePartnerBalls;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.SwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Shoots 9 balls centered on the goal then the chassis drives to the generator and we intake 3 balls
 * @author Madison J. 
 * @category AUTON 
 */
public class CenterGoal9BallTurn extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final SwerveDrivetrain swerveDrivetrain;
    private final OperatorAngleAdjustment operatorAngleAdjustment;
    private final Pigeon pigeon;

        /**
     * Shoots 9 balls centered on the goal then the chassis drives to the generator
     * and we intake 3 balls
     */
    public CenterGoal9BallTurn(double delay, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, OperatorAngleAdjustment operatorAngleAdjustment) {
      this.swerveDrivetrain = swerveDrivetrain;
      this.operatorAngleAdjustment = operatorAngleAdjustment;
      this.pigeon = pigeon;

    final class FirstDrive {
        public static final double moduleAngle = 90, driveDist = 50, forward = -0.35, strafe = 0, driveTimeout = 5;
      }


      
    addCommands(
      new WaitCommand(delay).withTimeout(delay),
      new FirePartnerBalls(2),
      new zeroDriveEncoders(swerveDrivetrain).withTimeout(0.04),
      new AutoDriveWithJoystickInput(swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, pigeon, operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout)
    );
  
  }

 
}
