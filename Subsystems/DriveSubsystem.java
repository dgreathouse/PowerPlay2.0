package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utility.Hw;
import org.firstinspires.ftc.teamcode.Utility.DAngle;
import org.firstinspires.ftc.teamcode.Utility.MyMath;
import org.firstinspires.ftc.teamcode.Utility.SlewRateLimiter;
import org.firstinspires.ftc.teamcode.Utility.Vector2d;
import org.firstinspires.ftc.teamcode.Utility.k;

public class DriveSubsystem extends SubsystemBase {
    public static final Vector2d m_leftVec = new Vector2d(Math.cos(30.0 * (Math.PI / 180.0)), Math.sin(30.0 * (Math.PI / 180.0)));
    public static final Vector2d m_rightVec = new Vector2d(Math.cos(150 * (Math.PI / 180.0)), Math.sin(150 * (Math.PI / 180.0)));
    public static final Vector2d m_backVec = new Vector2d(Math.cos(270 * (Math.PI / 180.0)),  Math.sin(270 * (Math.PI / 180.0)));

    CommandOpMode m_opMode;

    double m_ySpeed, m_xSpeed, m_zRotation;
    public DriveSubsystem(CommandOpMode _opMode){
        m_opMode = _opMode;

    }
    public boolean m_isFieldOriented = true;
    public double m_FOMAngle = 0;
    public boolean m_isSlewLimited = false;



    public DriveSubsystem(){

    }
    public void driveCartesianIK(double _ySpeed, double _xSpeed, double _zRotation, double _gyroAngle){
        double leftSpeed = 0.0;
        double rightSpeed = 0.0;
        double backSpeed = 0.0;

        MyMath.clamp(_ySpeed, -1.0, 1.0);
        MyMath.clamp(_xSpeed, -1.0, 1.0);
        MyMath.clamp(_zRotation, -1.0, 1.0);

        m_zRotation = _zRotation;

        Vector2d input = new Vector2d(_ySpeed,_xSpeed);
        input.rotate(-_gyroAngle);

        leftSpeed = input.scalarProject(m_leftVec) + _zRotation;
        rightSpeed = input.scalarProject(m_rightVec) + _zRotation;
        backSpeed = input.scalarProject(m_backVec) + _zRotation;

        Hw.leftDrive.set(leftSpeed);
        Hw.rightDrive.set(rightSpeed);
        Hw.backDrive.set(backSpeed);
    }

    /* Each drive motor is a 435 RPM gearbox/motor.
    The Encoder does 384.5 PPR or counts per revolution
    The wheel diameter is 96mm or 3.78in
    The circumference is 301.6mm or 11.875in
    Inch/Count = 11.875/384.5 = 0.030885
     */
    public double getInchesDriven(DAngle _angle){
        double rtn = 0;
        double left = Hw.leftDrive.getDistance();
        double right = Hw.rightDrive.getDistance();
        double back = Hw.backDrive.getDistance();
        switch (_angle){
            case ang_0: // Left, Right
                rtn = (left - right)/2;
                break;
            case ang_180:
                rtn = -(left - right)/2;
                break;
            case ang_60: // Left, Back
                rtn = (left - back)/2;
                break;
            case ang_240:
                rtn = -(left - back)/2;
                break;
            case ang_120: // Right, Back
                rtn = -(-right + back)/2;
                break;
            case ang_300:
                rtn = (-right + back)/2;
                break;
            default:
                rtn = 0;
                break;
        }
        return rtn;
    }
   public double getAngle(DAngle _angle){
        double rtn = 0.0;
        switch (_angle){
            case ang_0:
                rtn = 0;
                break;
            case ang_60:
                rtn = 60;
                break;
            case ang_120:
                rtn = 120;
                break;
            case ang_180:
                rtn = 180;
                break;
            case ang_240:
                rtn = 240;
                break;
            case ang_300:
                rtn = 300;
                break;
        }
        return rtn;
    }
    public void resetGyro(){
        Hw.imu.reset();
    }
    public void setGyro(){
        double angle = MyMath.getAngle(Hw.gpOperator.getLeftX(), Hw.gpOperator.getLeftY());
        m_FOMAngle = angle;
        Hw.imu.setOffset(angle);
    }
    public void setGyro(double X, double Y){
        double angle = MyMath.getAngle(X, Y);
        m_FOMAngle = angle;
        Hw.imu.setOffset(angle);
    }
    public void setIsFieldOriented(){
        m_isFieldOriented = !m_isFieldOriented;
    }
    public boolean getIsFieldOriented() {
        return m_isFieldOriented;
    }
    public void setSpeedRatio(double _ratio){
        k.DRIVE.DriveScale = _ratio;
    }
    public void resetMotors(){
        Hw.leftDrive.resetEncoder();
        Hw.rightDrive.resetEncoder();
        Hw.backDrive.resetEncoder();

        Hw.leftDrive.setRunMode(Motor.RunMode.RawPower);
        Hw.rightDrive.setRunMode(Motor.RunMode.RawPower);
        Hw.backDrive.setRunMode(Motor.RunMode.RawPower);
    }

    public void toggleSlewRateLimiter(){
        m_isSlewLimited = !m_isSlewLimited;
    }
    @Override
    public void periodic() {
        m_opMode.telemetry.addData("Left Inches = ", Hw.leftDrive.getDistance());
        m_opMode.telemetry.addData("Right Inches = ", Hw.rightDrive.getDistance());
        m_opMode.telemetry.addData("Back Inches = ", Hw.backDrive.getDistance());
        m_opMode.telemetry.addData("Robot Ang Deg = ", -Hw.imu.getAbsoluteHeading());
        m_opMode.telemetry.addData("zRotation = ", m_zRotation);
        m_opMode.telemetry.addData("FOM Angle = ", m_FOMAngle);

    }

}
