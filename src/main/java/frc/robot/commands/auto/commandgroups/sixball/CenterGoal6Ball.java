package frc.robot.commands.auto.commandgroups.sixball;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.AutoDriveWithJoystickInput;
import frc.robot.commands.auto.zeroDriveEncoders;
import frc.robot.commands.auto.commandgroups.common.systemactions.FireOnePartnerBall;
import frc.robot.commands.intake.runIntake;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.KickerWheel;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.Vision;

/**
 * Shoots 9 balls centered on the goal then the chassis drives to the generator and we intake 3 balls
 * @author Madison J. 
 * @category AUTON 
 */
public class CenterGoal6Ball extends SequentialCommandGroup {
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
     * Shoots 9 balls centered on the goal then the chassis drives to the generator
     * and we intake 3 balls
     */
    public CenterGoal6Ball(double delay, Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
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
        public static final double moduleAngle = 0, driveDist = 35, forward = -0.35, strafe = 0, driveTimeout = 5;
      }


      
    addCommands(
      new runIntake(m_intake, Constants.INTAKEFORWARDSPEED),
      new WaitCommand(2).withTimeout(2),
      new FireOnePartnerBall(1, m_operatorAngleAdjustment, m_agitator, m_serializer, m_shooter, m_kickerWheel,  m_intake),
      new zeroDriveEncoders(m_swerveDrivetrain).withTimeout(0.04),
      new ParallelRaceGroup(
        new runSerializer(m_serializer, Constants.SERIALIZERDRIVERFORWARDSPEED),
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, FirstDrive.driveDist, FirstDrive.forward, FirstDrive.strafe, FirstDrive.moduleAngle, m_pigeon, m_operatorAngleAdjustment).withTimeout(FirstDrive.driveTimeout)
      )
    );
  
  }

 
}
