/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swerve;

import frc.robot.RobotContainer;
import frc.robot.subsystems.OperatorAngleAdjustment;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Vision;
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
  private final Vision vision;
  private final Pigeon pigeon;
  private boolean isRotating;

  /**
   * Sets the robot's angle offset. This should be on the <b>DRIVER</b> joystick
   * @param subsystem - OperatorAngleAdjustment Subsystem Object from Robot
   * @param isRotating - determines if the robot will be rotating when the button is pressed
   */
  public ChangeGyroAngleOffset(OperatorAngleAdjustment operatorAngleAdjustment, boolean isRotating, Vision vision, Pigeon pigeon) {
    this.OperatorAngleAdjustment = operatorAngleAdjustment;
    this.vision = vision;
    this.pigeon = pigeon;
    this.isRotating = isRotating;
    addRequirements(operatorAngleAdjustment);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    OperatorAngleAdjustment.setOffsetAngle(OperatorAngleAdjustment.getGyroAngleOffset());
    OperatorAngleAdjustment.setIsChangingGyroAngle(isRotating);
    if (vision.getRotateLimelight() && isRotating) {
      vision.setLEDMode(3);
      OperatorAngleAdjustment.setLimelightRotationMode(true);
    } else {
      vision.setLEDMode(1);
      OperatorAngleAdjustment.setLimelightRotationMode(false);
    }

    if (!isRotating) {
      vision.setLEDMode(1);
      if (OperatorAngleAdjustment.getMode().equals("targetLimelightOn")) {
        OperatorAngleAdjustment.setOffsetAngle(-pigeon.getYawMod());
        OperatorAngleAdjustment.setLimelightRotationMode(false);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

}