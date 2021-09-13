package frc.robot.commands.auto.commandgroups.common.movement;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.commands.auto.*;

/**
 * Drives from the initiation line to the Trench to gather power cells
 * @author Madison J. 
 * @category AUTON 
 */
public class PreTrenchGoalOffset extends SequentialCommandGroup {
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  public double intakeSpeed = 0.5;

  /**
   * Drives from the initiation line to the generator command group
   */
  public PreTrenchGoalOffset(OperatorAngleAdjustment operatorAngleAdjustment, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon) {
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    final class FirstDrive {
      public static final double robotAngle = 90, driveDist = 77, forward = 0.45, strafe = -0.85, driveTimeout = 5;
    }


    addCommands(
      new resetDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.robotAngle, m_pigeon,m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout)
    );
  }
}
