package frc.robot.commands.auto;

import frc.robot.subsystems.Serializer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Sets the serializer speed
 * @author Nicholas Stokes
 */
public class runSerializerToSecondSensor extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Serializer subsystem;
  private double serializerSpeed;

  /**
   * Sets the serializer speed 
   * @author Nicholas Stokes
   * @param serializer The subsystem used by this command. (Serializer)
   * @param speed  A double number that sets what speed the motors move at
   */
  public runSerializerToSecondSensor(Serializer serializer, double speed) {
    subsystem = serializer;
    serializerSpeed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // This will set the serializer to run at a set speed
    subsystem.setSerializerSpeed(serializerSpeed);
  }

  public void execute() {
    if(subsystem.middleSerializerSensor.get()) {
      subsystem.setSerializerSpeed(0);
    } else {
      subsystem.setSerializerSpeed(serializerSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.stopSerializer();
  }

  @Override
  public boolean isFinished() {
    return subsystem.middleSerializerSensor.get();
  }
}
