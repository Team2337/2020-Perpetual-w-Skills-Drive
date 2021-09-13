package frc.robot.commands.auto.commandgroups.common.systemactions;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.zeroAngleEncoders;
import frc.robot.subsystems.SwerveDrivetrain;

/**
 * Sets the angle encoders to zero command group
 * @author Madison J. 
 * @category AUTON 
 */
public class SendModulesToZero extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SwerveDrivetrain m_swerveDrivetrain;
 /**
 * Sets the angle encoders to zero command group
 */
  public SendModulesToZero(SwerveDrivetrain swerveDrivetrain) {
    m_swerveDrivetrain = swerveDrivetrain;
    addCommands(
    // new zeroWithAnalog(m_swerveDrivetrain),
    new zeroAngleEncoders(m_swerveDrivetrain)
    );
  
  }

 
}
