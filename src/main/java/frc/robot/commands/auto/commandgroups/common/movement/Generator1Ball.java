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
public class Generator1Ball extends SequentialCommandGroup {
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
  public Generator1Ball(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
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
      public static final double driveDist = 12, forward = 0, strafe = -0.3, robotAngle = 5;
    }

    final class FirstRotate {
      public static final double robotAngle = 0;
    }

    addCommands(
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, FirstRotate.robotAngle, operatorAngleAdjustment, pigeon),
      new ParallelCommandGroup(
        new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
        new runAgitator(m_agitator, Constants.AGITATORSPEED),
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, FourthDrive.driveDist, FourthDrive.forward, FourthDrive.strafe, FourthDrive.robotAngle, pigeon, operatorAngleAdjustment)
      )

    );
  }
}
