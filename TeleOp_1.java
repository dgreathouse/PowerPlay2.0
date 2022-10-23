package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.ArmDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.ClawDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.LiftDefaultCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.Utility.Hw;

@TeleOp(name = "TeleOp 1", group = "Linear OpMode")
public class TeleOp_1 extends CommandOpMode {

    DriveSubsystem drive;
    LiftSubsystem lift;
    ClawSubsystem claw;
    ArmSubsystem arm;
    @Override
    public void initialize() {

        // Initialize hardware
        Hw hw = new Hw(this);
        hw.init();
        Hw.imu.reset();

        // Create Subsystems
        drive = new DriveSubsystem(this);
        lift = new LiftSubsystem(this);
        claw = new ClawSubsystem(this);
        arm = new ArmSubsystem(this);

        // Create Commands
        DriveDefaultCommand driveDefaultCommand = new DriveDefaultCommand(this, drive);
        LiftDefaultCommand liftDefaultCommand = new LiftDefaultCommand(this, lift);
        ClawDefaultCommand clawDefaultcommand = new ClawDefaultCommand(this, claw);
        ArmDefaultCommand armDefaultCommand = new ArmDefaultCommand(this, arm);

        Hw.gpOperator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .and(Hw.gpOperator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).negate())
                        .whenActive(new InstantCommand(() ->claw.open(), claw));
        Hw.gpOperator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .and(Hw.gpOperator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).negate())
                .whenActive(new InstantCommand(() -> claw.close(), claw));
        Hw.gpDriver.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> drive.setIsFieldOriented()));

        Hw.gpDriver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> drive.resetGyro()));
        Hw.gpOperator.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> drive.setGyro()));

        // register Subsystems
        register(drive, lift, claw, arm);

        // Set Default Commands
        drive.setDefaultCommand(driveDefaultCommand);
        lift.setDefaultCommand(liftDefaultCommand);
        claw.setDefaultCommand(clawDefaultcommand);
        arm.setDefaultCommand(armDefaultCommand);
    }

    @Override
    public void runOpMode() throws InterruptedException{
        initialize();

        waitForStart();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
            telemetry.update();
        }
        reset();
    }
}
