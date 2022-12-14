package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Utility.DAngle;
import org.firstinspires.ftc.teamcode.Utility.Hw;
import org.firstinspires.ftc.teamcode.Utility.RGB;
import org.firstinspires.ftc.teamcode.Utility.k;

public class ColorSensorSubsystem extends SubsystemBase {
    CommandOpMode m_opMode;
    RGB rgb = new RGB();
    public ColorSensorSubsystem(CommandOpMode _opMode){
        m_opMode = _opMode;
    }
    public int senseColor(){
        int rtn = 1;
        int r = Hw.colorSensor.red();
        int g = Hw.colorSensor.green();
        int b = Hw.colorSensor.blue();

        if(r > g && r > b && r > k.COLOR.ColorRedLimit){
            rtn = 1;
        }else if(g > r && g > b && g > k.COLOR.ColorGreenLimit){
            rtn = 2;
        }else if(b > g && b > r && b > k.COLOR.ColorBlueLimit){
            rtn = 3;
        }
        k.COLOR.ColorNumber = rtn;

        return rtn;
    }
    public RGB getColors(){
        rgb.R =  Hw.colorSensor.red();
        rgb.G = Hw.colorSensor.green();
        rgb.B = Hw.colorSensor.blue();

        return rgb;
    }
    public DAngle getAutoAngle(){
        if(k.COLOR.ColorNumber == 3){
            return DAngle.ang_180;
        }
        return DAngle.ang_0;
    }
    @Override
    public void periodic(){
        m_opMode.telemetry.addData("Red = ", Hw.colorSensor.red());
        m_opMode.telemetry.addData("Green = ", Hw.colorSensor.green());
        m_opMode.telemetry.addData("Blue = ", Hw.colorSensor.blue());
        m_opMode.telemetry.addData("Location = ", k.COLOR.ColorNumber);


    }
}
