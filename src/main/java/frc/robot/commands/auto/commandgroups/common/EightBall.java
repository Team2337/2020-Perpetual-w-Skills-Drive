package frc.robot.commands.auto.commandgroups.common;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Agitator.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.KickerWheel.*;
import frc.robot.commands.Serializer.stopSerializer;
import frc.robot.commands.auto.*;
import frc.robot.commands.auto.commandgroups.common.movement.Generator3Ball;
import frc.robot.commands.auto.commandgroups.common.movement.InTrench3Ball;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class EightBall extends SequentialCommandGroup {
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
  public double intakeSpeed = 0.5;

  /**
   * Drives from the initiation line to the generator command group
   */
  public EightBall(double delay, Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
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
      public static final double moduleAngle = 0, driveDist = 85, forward = -0.30, strafe = 0, driveTimeout = 7;
    }
    final class SecondDrive {
      public static final double moduleAngle = 0, driveDist = 25, forward = 0.0, strafe = -0.3, driveTimeout = 7;
    }

    final class ThirdDrive {
      public static final double driveDist = 75, forward = 0.3, strafe = 0.175;
    }

    addCommands(
      new WaitCommand(delay).withTimeout(delay),
      new stopSerializer(m_serializer),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SecondDrive.driveDist, SecondDrive.forward, SecondDrive.strafe, SecondDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SecondDrive.driveTimeout),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, -64, m_operatorAngleAdjustment, m_pigeon),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new Generator3Ball(ThirdDrive.driveDist, ThirdDrive.strafe, ThirdDrive.forward, m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel, m_swerveDrivetrain, m_pigeon, m_vision)
    );
  }
}
