package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.Utility.Hw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Utility.k;

public class DriveDefaultCommand extends CommandBase {

    private final DriveSubsystem m_driveSubsystem;
    CommandOpMode m_opMode;

    double x,y,z, ang;

    public DriveDefaultCommand(CommandOpMode _opMode, DriveSubsystem _driveSubsystem){
        m_driveSubsystem = _driveSubsystem;
        m_opMode = _opMode;
        addRequirements(m_driveSubsystem);
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        y = -Hw.gpDriver.getLeftY() * k.DRIVE.DriveScale;
        x = -Hw.gpDriver.getLeftX() * k.DRIVE.DriveScale;;
        z = -Hw.gpDriver.getRightX() * k.DRIVE.RotationScale;

        ang = -Hw.imu.getHeading();

        if(!m_driveSubsystem.getIsFieldOriented()){
            ang = 0.0;
        }
        m_driveSubsystem.driveCartesianIK(y,x,z,ang);
    }
}
