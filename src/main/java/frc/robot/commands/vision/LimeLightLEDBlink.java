package frc.robot.commands.vision;

import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Limelight LED will blink
 * <p><br/>Mode 2 turns off the LEDs</p>
 * @author Zayd A. & Madison J.
 * @category VISION
 */
public class LimeLightLEDBlink extends InstantCommand {
  private Vision subsystem;
  /**
   * Limelight LED will blink
   * <p><br/>Mode 2 turns off the LEDs</p>
   */
  public LimeLightLEDBlink(Vision subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    subsystem.setLEDMode(2);
  }

}