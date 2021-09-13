package frc.robot.commands.auto.commandgroups.common.movement;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.Vision.limeLightLEDOn;
import frc.robot.commands.Vision.limelightPipeline;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class PostTrench6Ball extends SequentialCommandGroup {
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
   private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  public double intakeSpeed = 0.5;

  /**
   * Drives from the initiation line to the generator command group
   */
  public PostTrench6Ball(OperatorAngleAdjustment operatorAngleAdjustment, Serializer serializer, Shooter shooter,  SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_serializer = serializer;
    m_shooter = shooter;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;

    /* --- Drives --- */
    final class FourthDrive {
      public static final double robotAngle = 90, driveDist = 10, forward = 0.4, strafe = -0.4, driveTimeout = 5;
    }

    /*
    final class FifthDrive {
      public static final double robotAngle = 12, driveDist = 5, forward = 0.325, strafe = -0.075, driveTimeout = 5;
    }
    */

    /* --- Rotate --- */
    final class FirstRotate {
      public static final double robotAngle = 20; //12
    }

    addCommands(
      new resetDriveEncoders(m_swerveDrivetrain),
      new limeLightLEDOn(m_vision),
      new limelightPipeline(m_vision, 1),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FourthDrive.driveDist, FourthDrive.forward, FourthDrive.strafe, FourthDrive.robotAngle, m_pigeon,m_operatorAngleAdjustment).withTimeout(FourthDrive.driveTimeout),
      // new stopIntake(Robot.Intake),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, FirstRotate.robotAngle, m_operatorAngleAdjustment, m_pigeon),
      // new resetDriveEncoders(m_swerveDrivetrain),
      // new AutoDriveWithJoystickInput(m_swerveDrivetrain, FifthDrive.driveDist, FifthDrive.forward, FifthDrive.strafe, FifthDrive.robotAngle).withTimeout(FifthDrive.driveTimeout), 
       new ParallelCommandGroup(
        new AutoRotateWithVision(m_swerveDrivetrain, 1, m_operatorAngleAdjustment, m_shooter, m_vision, m_pigeon).withTimeout(2.0),
        new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED)
       )
    ); 
  }
}
