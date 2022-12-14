package org.firstinspires.ftc.teamcode.Utility;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Hw {
    public static MotorEx leftDrive = null;
    public static MotorEx rightDrive = null;
    public static MotorEx backDrive = null;
    public static MotorEx lift = null;
    public static SimpleServo clawServo = null;
    //public static SimpleServo rightClaw = null;
    public static ServoImplEx liftEx = null;
    public static BNOimu imu;
    public static GamepadEx gpDriver, gpOperator;
    public static RevColorSensorV3 colorSensor;
    public static DigitalChannel liftDIOTop;
    public static DigitalChannel liftDIOBot;
    private LinearOpMode opMode = null;

    public Hw(LinearOpMode _opMode) {
        opMode = _opMode;
    }
    public void init(){

        leftDrive = new MotorEx(opMode.hardwareMap, "l", Motor.GoBILDA.RPM_435);
        leftDrive.setInverted(false);
        leftDrive.setRunMode(Motor.RunMode.RawPower);
        leftDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        leftDrive.encoder.setDirection(Motor.Direction.REVERSE);

        rightDrive = new MotorEx(opMode.hardwareMap, "r", Motor.GoBILDA.RPM_435);
        rightDrive.setInverted(true);
        rightDrive.setRunMode(Motor.RunMode.RawPower);
        rightDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        rightDrive.encoder.setDirection(Motor.Direction.FORWARD);

        backDrive = new MotorEx(opMode.hardwareMap, "b", Motor.GoBILDA.RPM_435);
        backDrive.setInverted(false);
        backDrive.setRunMode(Motor.RunMode.RawPower);
        backDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        backDrive.encoder.setDirection(Motor.Direction.REVERSE);

        lift = new MotorEx(opMode.hardwareMap,"lift", Motor.GoBILDA.RPM_312);
        lift.setInverted(false);
        lift.setRunMode(Motor.RunMode.RawPower);
        lift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift.setDistancePerPulse(k.LIFT.InchPerCnt);
        lift.encoder.getDistance();

        liftDIOTop = opMode.hardwareMap.get(DigitalChannel.class, "diot");
        liftDIOTop.setMode(DigitalChannel.Mode.INPUT);

        liftDIOBot = opMode.hardwareMap.get(DigitalChannel.class, "diob");
        liftDIOBot.setMode(DigitalChannel.Mode.INPUT);

        clawServo = new SimpleServo(opMode.hardwareMap, "c", 0, 270);

        liftEx = opMode.hardwareMap.get(ServoImplEx.class, "lex");
        liftEx.resetDeviceConfigurationForOpMode();

        PwmControl.PwmRange pwmr = new PwmControl.PwmRange(800,2200);
        liftEx.setPwmRange(pwmr);
        liftEx.scaleRange(0.2,0.8);

        imu = new BNOimu(opMode.hardwareMap);
        imu.init();

        colorSensor = opMode.hardwareMap.get(RevColorSensorV3.class, "cs");

        gpDriver = new GamepadEx(opMode.gamepad1);
        gpOperator = new GamepadEx(opMode.gamepad2);

        opMode.telemetry.addData(">", "Hardware Initialized");


    }
}
