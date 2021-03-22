package frc.robot.commands.auto.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.DriveWithJoystickInputs;
import frc.robot.subsystems.SwerveDrivetrain;

public class CircleTest extends SequentialCommandGroup {
    SwerveDrivetrain drivetrain;

    public CircleTest() {
        addCommands(
        new DriveWithJoystickInputs(drivetrain, .4, 75.0, 0),
        new DriveWithJoystickInputs(drivetrain, .4, 75.0, 22)

        );
    }
    
}
