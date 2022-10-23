package org.firstinspires.ftc.teamcode.Utility;

public class MyMath {
    public static double clamp(double _input, double _min, double _max){
        double rtn = _input;
        if(_input < _min) {
            rtn = _min;
        }else if(_input > _max){
            rtn = _max;
        }
        return rtn;
    }
    public static double getAngle(double x, double y){

        return (Math.atan2(y,x)) * (180 / Math.PI);
    }
}
