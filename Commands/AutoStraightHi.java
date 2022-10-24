package org.firstinspires.ftc.teamcode.Commands;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.util.Direction;

import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.Utility.ClawEnum;
import org.firstinspires.ftc.teamcode.Utility.DAngle;
import org.firstinspires.ftc.teamcode.Utility.k;

public class AutoStraightHi extends SequentialCommandGroup {

    public AutoStraightHi(CommandOpMode _opMode, DriveSubsystem _drive, LiftSubsystem _lift, ClawSubsystem _claw, ColorSensorSubsystem _color, ArmSubsystem _arm, Direction _side) {
        DAngle toHiJunction = DAngle.ang_60;
        DAngle awayHiJunction = DAngle.ang_240;
        double rotateSign = -1.0;

        if(_side == Direction.RIGHT){
            toHiJunction = DAngle.ang_300;
            awayHiJunction = DAngle.ang_120;
            rotateSign = 1.0;
        }
        addCommands(
            new ParallelCommandGroup(
                    // Close the claw to grab the cone
                    new ClawAutoCommand(_opMode, _claw, ClawEnum.CLOSE),
                    // Drive Forward away from wall
                    new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.5, 8, 2.0)
            ),
            new ParallelCommandGroup(
                    // The lift servo should exit immediately but the command should continue to the servo
                    new ArmAutoExtendCommand(_opMode,_arm, k.ARM.AutoExtendAngle),
                    // Raise Lift to take up the slack
                    new LiftAutoMoveCommand(_opMode, _lift, 3.75, 0.19, 2)
            ),
            // Reset the encoder so 0 is all the way down
            new LiftAutoResetEncoder(_opMode,_lift),
            // Raise lift to above the Cone
            new LiftAutoMoveCommand(_opMode, _lift, 10, 0.75, 2),
            new ParallelCommandGroup(
                    // Raise lift to above the Cone
                    new LiftAutoMoveCommand(_opMode, _lift, k.LIFT.ConeHeightHi, 0.65, 3),
                    // Drive forward to center line
                    new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, 8.5, 3.0)
            ),
            // Sense the color after a delay while pushing cone
            new ColorSensorSenseCommand(_opMode, _color,0.5),
            // Drive forward to center line
            new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.6, 22, 7.0),
            // Drive to Hi Junction at an angle
            new DriveAutoMoveCommand(_opMode,_drive, toHiJunction, 0.5, 10, 5.0),
            // Open the Claw to drop on Hi Junction
            new ClawAutoCommand(_opMode, _claw, ClawEnum.OPEN),
            // Drive back to line up with stack of 5 cones
            new DriveAutoMoveCommand(_opMode,_drive, awayHiJunction, 0.5, 10, 5.0),
            new ParallelCommandGroup(
                    // Rotate to the stack of 5 cones
                    new DriveAutoRotateCommand(_opMode, _drive, rotateSign * 90, 0.5, 5.0),
                    // Lower lift to Cone 5 height
                    new LiftAutoMoveCommand(_opMode, _lift, k.LIFT.ConeHeight5, 0.75, 3)
            ),
            new ParallelCommandGroup(
                    // Drive backwards to signal location to park
                    new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.75, -((k.COLOR.ColorNumber-2)*24), 3.0), //
                    // Lower Lift so the transition from Auto to Teleop does not let go of cone
                    new LiftAutoMoveCommand(_opMode,_lift,k.LIFT.ConeHeightJunction,0.5,4)
            )

        );
    }
}
