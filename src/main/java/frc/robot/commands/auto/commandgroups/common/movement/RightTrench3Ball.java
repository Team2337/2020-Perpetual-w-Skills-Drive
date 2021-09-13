package frc.robot.commands.auto.commandgroups.common.movement;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Agitator.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.KickerWheel.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class RightTrench3Ball extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
   private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  /**
   * Drives from the initiation line to the generator command group
   */
  public RightTrench3Ball(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;
    

    final class FirstDrive {
      public static final double moduleAngle = 90, driveDist = 57, forward = -0.35, strafe = 0.35, driveTimeout = 5;
    }

    final class SecondDrive {
      public static final double moduleAngle = 90, driveDist = 125, forward = -0.4, strafe = 0, driveTimeout = 5;
    }

    final class ThirdDrive {
      public static final double moduleAngle = 45, driveDist = 20, forward = 0.3, strafe = -0.3, driveTimeout = 5;
    }

    final class FirstRotate {
      public static final double moduleAngle = 12;
    }
    
    addCommands(
      new resetDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout),
      new ParallelCommandGroup(
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, SecondDrive.driveDist, SecondDrive.forward, SecondDrive.strafe, SecondDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SecondDrive.driveTimeout),
        new runIntake(m_intake, 1),
        new runAgitator(m_agitator, Constants.AGITATORSPEED),
        new WaitCommand(1).andThen(new autoStartShooter(m_shooter, Constants.SHOOTSPEEDCLOSE).andThen(new runKicker(m_kickerWheel)))
      ),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, ThirdDrive.driveDist, ThirdDrive.forward, ThirdDrive.strafe, ThirdDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(ThirdDrive.driveTimeout),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, FirstRotate.moduleAngle, m_operatorAngleAdjustment, m_pigeon), 
      new stopIntake(m_intake),
      new AutoRotateWithVision(m_swerveDrivetrain, 1, m_operatorAngleAdjustment, m_shooter, m_vision, m_pigeon),
      new stopShooter(m_shooter)
    );
  }
}
