package frc.robot.commands.auto.commandgroups;

import frc.robot.commands.auto.AutoDriveWithJoystickInput;
import frc.robot.commands.auto.AutoRotateWithJoystickInput;
import frc.robot.commands.auto.AutoStrafeWithPixy;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import frc.robot.commands.Agitator.runAgitator;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.commands.auto.commandgroups.common.systemactions.FirePartnerBalls;
import frc.robot.commands.intake.runIntake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * Shoots 9 balls centered on the goal then the chassis drives to the generator and we intake 3 balls
 * @author Madison J. 
 * @category AUTON 
 */
public class CenterGoal8BallGenerator extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    /**
     * Shoots 6 balls centered on the goal then the chassis backs up 35"
     */
    public CenterGoal8BallGenerator(double delay, Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision, Object Constants) {
      m_operatorAngleAdjustment = operatorAngleAdjustment;
      m_agitator = agitator;
      m_intake = intake;
      m_serializer = serializer;
      m_shooter = shooter;
      m_kickerWheel = kickerWheel;
      m_swerveDrivetrain = swerveDrivetrain;
      m_pigeon = pigeon;
      m_vision = vision;

    final class FirstDrive {
        public static final double moduleAngle = 0, driveDist = 100, forward = -0.30, strafe = 0, driveTimeout = 5;
      }
      final class SecondDrive {
        public static final double moduleAngle = 0, driveDist = 0, forward = -0.30, strafe = 27, driveTimeout = 5;
      }

      final class ThirdDrive {
        public static final double robotAngle = 90, forward = -0.4, strafe = 0, driveTimeout = 5;
      }

      
    addCommands(
      new WaitCommand(delay).withTimeout(delay),
      //new FirePartnerBalls(0, m_intake, m_agitator, m_serializer, m_operatorAngleAdjustment, m_shooter, m_kickerWheel),
      new stopSerializer(m_serializer),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SecondDrive.driveDist, SecondDrive.forward, SecondDrive.strafe, SecondDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SecondDrive.driveTimeout),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, -118, m_operatorAngleAdjustment, m_pigeon),
      new AutoStrafeWithPixy(m_swerveDrivetrain, driveDistance, speed, ThirdDrive.robotAngle, vision, operatorAngleAdjustment, pigeon),
      new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
        new runAgitator(m_agitator, Constants.AGITATORSPEED)
    );
  
  }

 
}
