package frc.robot.commands.auto.commandgroups.nineball;

import frc.robot.commands.auto.AutoDriveWithJoystickInput;
import frc.robot.commands.auto.zeroDriveEncoders;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.commands.auto.commandgroups.common.systemactions.FirePartnerBalls;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Shoots 9 balls centered on the goal then the chassis drives to the generator and we intake 3 balls
 * @author Madison J. 
 * @category AUTON 
 */
public class CenterGoal9Ball extends SequentialCommandGroup {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })


    private final Intake intake;
    private final SwerveDrivetrain swerveDrivetrain;
    private final OperatorAngleAdjustment operatorAngleAdjustment;
    private final Pigeon pigeon;
    private final RobotContainer robotContainer;
    /**
     * Shoots 9 balls centered on the goal then the chassis drives to the generator
     * and we intake 3 balls
     */
    public CenterGoal9Ball(double delay, Intake intake, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, OperatorAngleAdjustment operatorAngleAdjustment, RobotContainer robotContainer) {
      this.intake = intake;
      this.swerveDrivetrain = swerveDrivetrain;
      this.operatorAngleAdjustment = operatorAngleAdjustment;
      this.pigeon = pigeon;
      this.robotContainer = robotContainer;

        
    final class FirstDrive {
        public static final double moduleAngle = 0, driveDist = 35, forward = -0.35, strafe = 0, driveTimeout = 5;
      }


      
    addCommands(
      new runIntake(intake, Constants.INTAKEFORWARDSPEED),
      new WaitCommand(2).withTimeout(2),
      new FirePartnerBalls(2, robotContainer.intake, robotContainer.agitator, robotContainer.serializer),
      new zeroDriveEncoders(swerveDrivetrain).withTimeout(0.04),
      new AutoDriveWithJoystickInput(swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, pigeon, operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout)
    );
  
  }

 
}
