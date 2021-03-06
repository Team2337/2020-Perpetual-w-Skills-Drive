/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.Constants.Vision;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Sets the robot's angle offset. This should be on the <b>DRIVER</b> joystick
 * @see OperatorAngleAdjustment
 * @author Bryce G., Madison J.
 * @category SWERVE
 */
public class ChangeVisionAngleOffset extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private RobotContainer robotContainer;
  private final OperatorAngleAdjustment OperatorAngleAdjustment;
  private final Pigeon pigeon;

  private boolean isRotating;

  /**
   * Sets the robot's angle offset. This should be on the <b>DRIVER</b> joystick
   * @param subsystem - OperatorAngleAdjustment Subsystem Object from Robot
   * @param isRotating - determines if the robot will be rotating when the button is pressed
   */
  public ChangeVisionAngleOffset(OperatorAngleAdjustment operatorAngleAdjustment, boolean isRotating, Pigeon pigeon) {
    this.OperatorAngleAdjustment = operatorAngleAdjustment;
    this.pigeon = pigeon;
    this.isRotating = isRotating;
    addRequirements(operatorAngleAdjustment);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (isRotating) {
      // Robot.Vision.setLEDMode(3);
      OperatorAngleAdjustment.setLimelightRotationMode(true);
    } else {
      // Robot.Vision.setLEDMode(1);
      OperatorAngleAdjustment.setLimelightRotationMode(false);
      //OperatorAngleAdjustment.setOffsetAngle(-Robot.Utilities.getPigeonYawMod());
      OperatorAngleAdjustment.setOffsetAngle(-pigeon.getYawMod());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

}