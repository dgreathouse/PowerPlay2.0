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

public class AutoMid extends SequentialCommandGroup {

    public AutoMid(CommandOpMode _opMode, DriveSubsystem _drive, LiftSubsystem _lift, ClawSubsystem _claw, ColorSensorSubsystem _color, ArmSubsystem _arm, Direction _side) {

        DAngle awayMidJunction = DAngle.ang_120;
        double toMidJunctionIn = 5;
        if(_side == Direction.RIGHT){
            awayMidJunction = DAngle.ang_240;
            toMidJunctionIn = 7;
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
                 new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, 12, 3.0),
                 new LiftAutoMoveTimeCommand(_opMode, _lift, 0.85, 3),
                 new ColorSensorSenseCommand(_opMode, _color,1)
            ),
            new DriveAutoRotateCommand(_opMode, _drive, -90, 0.5, 3.0),
            new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, toMidJunctionIn, 3.0),
            new ClawAutoCommand(_opMode, _claw, ClawEnum.OPEN),
            new DriveAutoMoveCommand(_opMode,_drive, awayMidJunction, 0.4, 15, 5.0),
            new ParallelCommandGroup(
                // Close the claw to insure lowering is ok
                new ClawAutoCommand(_opMode, _claw, ClawEnum.CLOSE),
                // Drive to location
                new DriveAutoMoveColorCommand(_opMode,_drive, DAngle.ang_0, 0.5, 3.0, _side),
                // Lower lift to the bottom
                new LiftAutoMoveTimeCommand(_opMode, _lift, -0.75, 3)
            ),
            new DriveAutoRotateCommand(_opMode, _drive, 0, 0.5, 3.0)
        );
    }
}
