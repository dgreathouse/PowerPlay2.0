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
    /** Autonomous command group for placing the cone on the closest Mid Junction
     *  Routine in short is:
     *  Drive away from wall and raise the Arm
     *  Drive forward and read the signal sleeve color
     *  Turn to Mid Junction, Drive to it and drop the cone
     *  Slide away to location 2 and drive to correct location
     *  Rotate to straight ahead for FOM in TeleOp
     *
     * @param _opMode   The OpMode of Auto or TeleOp
     * @param _drive    Drive Subsystem
     * @param _lift     Lift Subsystem
     * @param _claw     Claw Subsystem
     * @param _color    Color Subsystem
     * @param _arm      Arm Subsystem
     * @param _side     The side of the field the robot is lined up on.
     */
    public AutoMid(CommandOpMode _opMode, DriveSubsystem _drive, LiftSubsystem _lift, ClawSubsystem _claw, ColorSensorSubsystem _color, ArmSubsystem _arm, Direction _side) {
        double sign = 1.0;  // Used for which direction to turn for left or right
        DAngle awayMidJunction = DAngle.ang_120; // The angle to slide away from Junction for L/R
        double toMidJunctionIn = 2; // Inched to go to Mid since lineup is different on L/R
        // Change all the above numbers that default for left if on the right side
        if(_side == Direction.RIGHT){
            awayMidJunction = DAngle.ang_240;
            toMidJunctionIn = 5;
            sign = -1;
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
                 // Drive toward the signal cone
                 new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, 24.5, 7.0),
                 // Lift the claw above the cone and Mid junction
                 new LiftAutoMoveTimeCommand(_opMode, _lift, 1, .9),
                 // Sense the color of the signal sleeve
                 new ColorSensorSenseCommand(_opMode, _color,1.25)
            ),
            // Rotate to the Mid Junction
            new DriveAutoRotateCommand(_opMode, _drive, sign * 90, 0.35, 5.0),
            // Move to the Mid Junction
            new DriveAutoMoveCommand(_opMode,_drive, DAngle.ang_0, 0.4, toMidJunctionIn, 3.0),
            // Open the claw and drop the cone
            new ClawAutoCommand(_opMode, _claw, ClawEnum.OPEN),
            // Drive at angle to location 2
            new DriveAutoMoveCommand(_opMode,_drive, awayMidJunction, 0.4, 12, 5.0),
            new ParallelCommandGroup(
                // Close the claw to insure lowering is ok
                new ClawAutoCommand(_opMode, _claw, ClawEnum.CLOSE),
                // Drive to location
                new DriveAutoMoveColorCommand(_opMode,_drive, DAngle.ang_0, 0.5, 3.0, _side),
                // Lower lift below hinge
                new LiftAutoMoveTimeCommand(_opMode, _lift, -0.75, 1.25)
            ),
            // Rotate to straight ahead so Field Oriented Mode works for TeleOp
            new DriveAutoRotateCommand(_opMode, _drive, 0, 0.5, 4.0)
        );
    }
}
