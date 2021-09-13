package frc.robot.commands.auto.commandgroups.common.movement;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
import frc.robot.commands.Vision.limelightPipeline;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class PreTrenchPartnerOnRight extends SequentialCommandGroup {
  
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  public double intakeSpeed = 0.5;

  /**
   * Drives from the initiation line to the generator command group
   */
  public PreTrenchPartnerOnRight(OperatorAngleAdjustment operatorAngleAdjustment, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;

    final class FirstDrive {
      public static final double robotAngle = 0, driveDist = 36, forward = -0.4 /*-0.35*/, strafe = 0, driveTimeout = 5;
    }

    final class SecondDrive {
      public static final double robotAngle = 90, driveDist = 64, forward =  -0.075/* -0.2 */, strafe = 0.4/* 0.4 */, driveTimeout = 5;
    }

    addCommands(
      new limelightPipeline(m_vision, 1),
      new resetDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, SecondDrive.driveDist, SecondDrive.forward, SecondDrive.strafe, SecondDrive.robotAngle, m_pigeon, m_operatorAngleAdjustment),
      new limelightPipeline(m_vision, 0)
    );
  }
}
