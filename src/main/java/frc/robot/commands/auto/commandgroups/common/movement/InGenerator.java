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
import frc.robot.commands.Agitator.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.KickerWheel.*;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class InGenerator extends SequentialCommandGroup {
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
   * Drives from the initiation line to the generator command group
   */
  public InGenerator(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;

    final class FourthDrive {
      public static final double driveDist = 50, forward = -0.3, strafe = 0.08, robotAngle = -64;
    }

    final class FifthDrive {
      public static final double driveDist = 12, forward = 0.0, strafe = -0.3, driveTimeout = 7, robotAngle = -64;
    }

    addCommands(
      new zeroDriveEncoders(m_swerveDrivetrain),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FourthDrive.driveDist, FourthDrive.forward, FourthDrive.strafe, FourthDrive.robotAngle, pigeon, operatorAngleAdjustment),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FifthDrive.driveDist, FifthDrive.forward, FifthDrive.strafe, FifthDrive.robotAngle, pigeon, operatorAngleAdjustment),
      new zeroDriveEncoders(m_swerveDrivetrain)
    );
  }
}
