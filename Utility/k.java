package org.firstinspires.ftc.teamcode.Utility;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public final class k {
    public static final class DRIVE {
        public static final double InchPerCount = 0.0315;
        public static final double AngleLimit = 0.8;
        public static final PIDFCoefficients MovePIDFCoef= new PIDFCoefficients(0.25,0.075,0.0,0);
        //public static final PIDFCoefficients MovePIDFCoef= new PIDFCoefficients(0.06,0.00475,0.0,0);
        public static final PIDFCoefficients MoveRotatePIDFCoef= new PIDFCoefficients(.008,0,0,0);
        public static final PIDFCoefficients RotatePIDFCoef= new PIDFCoefficients(0.005,0.001,0,0);
        public static final double RotationScale = 0.5;
        public static double DriveScale = 0.5;
        public static double SlewRateLimit = 0.5;
        public static double AutoLocationDistance = 24;
    }

    public static final class LIFT {
        public static double LimitUp_In = 33.5;
        public static double LimitDown_In = 0.0;
        public static double SpeedRatio = 1.0;
        public static final double AutoPID_P = 0.3;
        public static final int ConeHeightLow = 13;
        public static final int ConeHeightMid = 25;
        public static final int ConeHeightHi = 33;
        public static final int ConeHeightJunction = 0;
        public static final int ConeHeight5 = 8;
        public static final double InchPerCnt = 0.008201937;
        public static double AutoLocationInches = 0.0;
        public static double AutoMaxSpeed = 1.0;

    }

    public static final class CLAW {
        public static final double Open = 155;  // Leave
        public static final double Close = 105;
        public static final double FullOpen = 175;
      //  public static final double RightOpen = 0;  // Leave
      //  public static final double RightClose = RightOpen + 60;
    }

    public static final class COLOR {
        public static final int ColorRedLimit = 300;
        public static final int ColorGreenLimit = 300;
        public static final int ColorBlueLimit = 300;
        public static int ColorNumber = 1;

    }
    public static final class ARM {
        public static final double AutoExtendAngle = 0;
    }


}
