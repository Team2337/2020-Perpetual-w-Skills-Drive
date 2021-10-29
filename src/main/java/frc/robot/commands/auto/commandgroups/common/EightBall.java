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
import frc.robot.commands.auto.commandgroups.common.movement.Generator1Ball;
import frc.robot.commands.auto.commandgroups.common.movement.Generator2Ball;
import frc.robot.commands.auto.commandgroups.common.movement.Generator3Ball;
import frc.robot.commands.auto.commandgroups.common.movement.InGenerator;
import frc.robot.commands.auto.commandgroups.common.movement.InTrench3Ball;
import frc.robot.commands.auto.commandgroups.common.movement.PostGenerator;
import frc.robot.commands.auto.commandgroups.common.systemactions.FirePreloads;

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

    final class FirstDrivePartOne {
      public static final double moduleAngle = 0, driveDist = 85, forward = -0.50, strafe = 0, driveTimeout = 7; //Drive distance was 157 at kettering, changed back at premier
    }
    final class FirstDrivePartTwo {
      public static final double moduleAngle = 0, driveDist = 3, forward = -0.40, strafe = 0, driveTimeout = 7; 
    }
    final class SecondDrive {
      public static final double moduleAngle = 0, driveDist = 10, forward = 0.0, strafe = -0.4, driveTimeout = 7;
    }

    final class ThirdDrive {
      public static final double driveDist = 60, forward = 0.4, strafe = 0.2333;
    }

    addCommands(
      new WaitCommand(delay).withTimeout(delay),
      new FirePreloads(m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel).withTimeout(2.75),
      new stopSerializer(m_serializer),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrivePartOne.driveDist, FirstDrivePartOne.forward, FirstDrivePartOne.strafe, FirstDrivePartOne.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrivePartOne.driveTimeout),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrivePartTwo.driveDist, FirstDrivePartTwo.forward, FirstDrivePartTwo.strafe, FirstDrivePartTwo.moduleAngle, pigeon, operatorAngleAdjustment),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SecondDrive.driveDist, SecondDrive.forward, SecondDrive.strafe, SecondDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(SecondDrive.driveTimeout),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new SlowAutoRotateWithJoystickInput(m_swerveDrivetrain, -64, m_operatorAngleAdjustment, m_pigeon).withTimeout(0.7),
      new zeroDriveEncoders(m_swerveDrivetrain),
      new Generator3Ball(ThirdDrive.driveDist, ThirdDrive.strafe, ThirdDrive.forward, m_intake, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel, m_swerveDrivetrain, m_pigeon, m_vision).withTimeout(4),
      new Generator1Ball(intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision),
      new WaitCommand(1).andThen(new autoStartShooter(m_shooter, Constants.SHOOTFRONTTRENCHAUTO).andThen(new runKicker(m_kickerWheel))),
      new PostGenerator(intake, operatorAngleAdjustment, agitator, serializer, shooter, kickerWheel, swerveDrivetrain, pigeon, vision)
    );
  }
}
