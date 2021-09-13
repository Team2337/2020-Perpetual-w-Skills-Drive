package frc.robot.commands.auto.commandgroups.common.movement;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
import frc.robot.commands.intake.runIntake;
import frc.robot.commands.KickerWheel.runKicker;
import frc.robot.commands.Serializer.runSerializer;
import frc.robot.commands.auto.AutoDriveWithJoystickInput;
import frc.robot.commands.auto.AutoRotateWithJoystickInput;
import frc.robot.commands.auto.AutoRotateWithVision;
import frc.robot.commands.auto.autoStartShooter;
import frc.robot.commands.auto.resetDriveEncoders;

/**
 * Drives from the initiation line to the generator command group
 * @author Madison J. 
 * @category AUTON 
 */
public class GeneratorThreeBallFromCenterTarget extends SequentialCommandGroup {
  private final Agitator m_agitator;
  private final Intake m_intake;
  private final Serializer m_serializer;
  private final OperatorAngleAdjustment m_operatorAngleAdjustment;
  private final KickerWheel m_kickerWheel;
  private final Shooter m_shooter;
  private final SwerveDrivetrain m_swerveDrivetrain;
  private final Pigeon m_pigeon;
  private final Vision m_vision;
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

 /**
 * Drives from the initiation line to the generator command group
 */
  public GeneratorThreeBallFromCenterTarget(Intake intake, OperatorAngleAdjustment operatorAngleAdjustment, Agitator agitator, Serializer serializer, Shooter shooter, KickerWheel kickerWheel, SwerveDrivetrain swerveDrivetrain, Pigeon pigeon, Vision vision) {
    m_operatorAngleAdjustment = operatorAngleAdjustment;
    m_agitator = agitator;
    m_intake = intake;
    m_serializer = serializer;
    m_shooter = shooter;
    m_kickerWheel = kickerWheel;
    m_swerveDrivetrain = swerveDrivetrain;
    m_pigeon = pigeon;
    m_vision = vision;

    /* --- Driving to Generator Left --- */
    int firstDriveDist = -140, firstDriveModuleAngles = 47;
    double firstDriveMaxSpeed = 0.7, firstDriveDriveP = 0.04, firstDriveAngleP = 1.1;
    addCommands(
      new resetDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, 155, -0.25, -0.3, 28, m_pigeon, m_operatorAngleAdjustment),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, 25, 0, 0, 28, m_pigeon, m_operatorAngleAdjustment).withTimeout(1),
      new resetDriveEncoders(m_swerveDrivetrain),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, 33, -0.2, 0.1, 28, m_pigeon, m_operatorAngleAdjustment).withTimeout(5),
      new runIntake(m_intake, 0.5),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, 53, 0.15, 0.3, 28, m_pigeon, m_operatorAngleAdjustment).withTimeout(5),
      new autoStartShooter(m_shooter, Constants.SHOOTSPEEDCLOSE),
      new AutoDriveWithJoystickInput(m_swerveDrivetrain, 33, 0.2, -0.1, 28, m_pigeon, m_operatorAngleAdjustment).withTimeout(5),
      new AutoRotateWithJoystickInput(m_swerveDrivetrain, 0, m_operatorAngleAdjustment, m_pigeon),
      new ParallelCommandGroup(
        new runKicker(m_kickerWheel),
        new runSerializer(m_serializer, 0.3),
        new AutoRotateWithVision(m_swerveDrivetrain, 1, m_operatorAngleAdjustment, m_shooter, m_vision, m_pigeon)
        )/* ,
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, 25, 0, 0, 28).withTimeout(1),
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, 50, 0.3, -0.15, 28).withTimeout(5),
        new AutoDriveWithJoystickInput(m_swerveDrivetrain, 25, 0, 0, 28).withTimeout(1),
        new AutoRotateWithJoystickInput(m_swerveDrivetrain, -15), */
        
    );
  
  }

 
}
