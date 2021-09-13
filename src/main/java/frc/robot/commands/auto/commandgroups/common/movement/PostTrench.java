package frc.robot.commands.auto.commandgroups.common.movement;

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
import frc.robot.commands.intake.*;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class PostTrench extends SequentialCommandGroup {
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
  public PostTrench(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {

    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;
    /* --- Drives --- */
    final class FourthDrive {
      public static final double robotAngle = 90, driveDist = 20, forward = 0.15, strafe = -0.15, driveTimeout = 5;
    }

    final class FifthDrive {
      public static final double robotAngle = 90, driveDist = 80, forward = 0.1, strafe = -0.35, driveTimeout = 5;
    }

    final class SixthDrive {
      public static final double robotAngle = 105, driveDist = 20, forward = 0.05, strafe = -0.175, driveTimeout = 5;
    }

    final class SeventhDrive {
      public static final double robotAngle = 105, driveDist = 20, forward = 0.35, strafe = -0.1, driveTimeout = 5;
    }

    final class EightDrive {
      public static final double robotAngle = 105, driveDist = 30, forward = -0.1, strafe = -0.35, driveTimeout = 5;
    }

    /* --- Rotate --- */
    final class FirstRotate {
      public static final double robotAngle = 12;
    }

    addCommands(
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FourthDrive.driveDist, FourthDrive.forward, FourthDrive.strafe, FourthDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FourthDrive.driveTimeout),
      new stopIntake(m_intake),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FifthDrive.driveDist, FifthDrive.forward, FifthDrive.strafe, FifthDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FifthDrive.driveTimeout),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SixthDrive.driveDist, SixthDrive.forward, SixthDrive.strafe, SixthDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SixthDrive.driveTimeout),
      new runIntake(m_intake, intakeSpeed),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SeventhDrive.driveDist, SeventhDrive.forward, SeventhDrive.strafe, SeventhDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SeventhDrive.driveTimeout),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, FirstRotate.robotAngle, m_operatorAngleAdjustment, m_pigeon), 
      new AutoRotateWithVision(m_swerveDrivetrain, 1, m_operatorAngleAdjustment, m_shooter, m_vision, m_pigeon),
      new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED)
    ); 
  }
}
