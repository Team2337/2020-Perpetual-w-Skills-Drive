/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swerve;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Sets the robot's angle offset. This should be on the <b>DRIVER</b> joystick
 * @see OperatorAngleAdjustment
 * @author Bryce G., Madison J.
 * @category SWERVE
 */
public class ChangeGyroAngleOffset extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OperatorAngleAdjustment OperatorAngleAdjustment;
  private RobotContainer robotContainer;
  private boolean isRotating;

  /**
   * Sets the robot's angle offset. This should be on the <b>DRIVER</b> joystick
   * @param subsystem - OperatorAngleAdjustment Subsystem Object from Robot
   * @param isRotating - determines if the robot will be rotating when the button is pressed
   */
  public ChangeGyroAngleOffset(OperatorAngleAdjustment operatorAngleAdjustment, boolean isRotating) {
    this.OperatorAngleAdjustment = operatorAngleAdjustment;
    this.isRotating = isRotating;
    addRequirements(operatorAngleAdjustment);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    OperatorAngleAdjustment.setOffsetAngle(OperatorAngleAdjustment.getGyroAngleOffset());
    OperatorAngleAdjustment.setIsChangingGyroAngle(isRotating);
    if (robotContainer.vision.getRotateLimelight() && isRotating) {
      robotContainer.vision.setLEDMode(3);
      OperatorAngleAdjustment.setLimelightRotationMode(true);
    } else {
      robotContainer.vision.setLEDMode(1);
      OperatorAngleAdjustment.setLimelightRotationMode(false);
    }

    if (!isRotating) {
      robotContainer.vision.setLEDMode(1);
      if (OperatorAngleAdjustment.getMode().equals("targetLimelightOn")) {
        OperatorAngleAdjustment.setOffsetAngle(-robotContainer.pigeon.getYawMod());
        OperatorAngleAdjustment.setLimelightRotationMode(false);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

}