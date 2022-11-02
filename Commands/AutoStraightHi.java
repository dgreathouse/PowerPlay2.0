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

        if(_side == Direction.RIGHT){
            toHiJunction = DAngle.ang_300;
            awayHiJunction = DAngle.ang_120;
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
                new LiftAutoMoveTimeCommand(_opMode, _lift, 0.35, 3)
            ),
            new ParallelCommandGroup(
                 new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, 32, 5.0),
                 new LiftAutoMoveTimeCommand(_opMode, _lift, 0.75, 3),
                 new ColorSensorSenseCommand(_opMode, _color,1.5)
            ),
            new DriveAutoMoveCommand(_opMode,_drive, toHiJunction, 0.4, 9, 5.0),
            new ClawAutoCommand(_opMode, _claw, ClawEnum.OPEN),
            new DriveAutoMoveCommand(_opMode,_drive, awayHiJunction, 0.4, 12, 5.0),
            new ParallelCommandGroup(
                // Close the claw to insure lowering is ok
                new ClawAutoCommand(_opMode, _claw, ClawEnum.CLOSE),
                // Rotate to the left
                new DriveAutoRotateCommand(_opMode, _drive, -90, 0.5, 5.0),
                // Lower lift to the bottom
                new LiftAutoMoveTimeCommand(_opMode, _lift, -0.75, 3)
            ),
            new DriveAutoMoveCommand(_opMode,_drive, _color.getAutoAngle(), 0.5, -((k.COLOR.ColorNumber-2)*20), 3.0),
            new DriveAutoRotateCommand(_opMode, _drive, 0, 0.5, 3.0)

        );
    }
}
