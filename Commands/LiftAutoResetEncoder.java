package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.Utility.Hw;

public class LiftAutoResetEncoder extends CommandBase {
    LiftSubsystem m_lift;
    CommandOpMode m_opMode;
    public LiftAutoResetEncoder(CommandOpMode _opMode, LiftSubsystem _lift){
        addRequirements(_lift);
        m_lift = _lift;
        m_opMode = _opMode;
    }
    @Override
    public void initialize(){
        Hw.lift.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hw.lift.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // m_lift.resetEncoder();
    }
    @Override
    public void execute(){

    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
