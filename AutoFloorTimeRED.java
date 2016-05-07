package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Leah on 11/18/2015.
 */
public class AutoFloorTimeRED extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;


    public void runOpMode() throws InterruptedException {

        motorRight = hardwareMap.dcMotor.get("RightMotor");
        motorLeft = hardwareMap.dcMotor.get("LeftMotor");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(2500);

        motorLeft.setPower(0);
        motorRight.setPower(0.5);
        sleep(850);


        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(3000);


        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}

