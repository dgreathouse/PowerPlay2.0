package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.Utility.Hw;
import org.firstinspires.ftc.teamcode.Utility.MyMath;
import org.firstinspires.ftc.teamcode.Utility.PIDFController;

public class LiftAutoMoveTimeCommand extends CommandBase {
    LiftSubsystem m_lift;
    CommandOpMode m_opMode;
    double m_timeOut;
    double m_speed;
    ElapsedTime m_elapsedTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public LiftAutoMoveTimeCommand(CommandOpMode _opMode, LiftSubsystem _lift, double _speed, double _timeOut){
        addRequirements(_lift);
        m_lift = _lift;
        m_opMode = _opMode;
        m_speed = _speed;
        m_timeOut = _timeOut;

    }
    @Override
    public void initialize(){

        m_elapsedTimer.reset();
    }
    @Override
    public void execute(){
        m_lift.move(m_speed);
    }
    @Override
    public boolean isFinished(){
        if(m_elapsedTimer.seconds() > m_timeOut){
            m_lift.move(0);
            return true;
        }
        return false;
    }
}
