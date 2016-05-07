package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Leah on 11/18/2015.
 *
 * This program uses the following sensors:
 * Modern Robotics Color Sensor ("mr")
 * Modern Robotics Touch Sensor ("mt")
 */
public class AutoBeaconTimeColorBLUE extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor colorSensor;
    Servo arm;
    Servo claw;

   // TouchSensor button;


    public void runOpMode() throws InterruptedException {

        motorRight = hardwareMap.dcMotor.get("RightMotor");
        motorLeft = hardwareMap.dcMotor.get("LeftMotor");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        colorSensor = hardwareMap.colorSensor.get("mr");
        arm = hardwareMap.servo.get("beacon");
        claw = hardwareMap.servo.get("servo_6");
        //button = hardwareMap.touchSensor.get("mt");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};

        waitForStart();

        arm.setPosition(0);  //Turn arm horizontally

        //Move straight towards line
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(2000);

        //Turn right
        motorLeft.setPower(0.5);
        motorRight.setPower(0);
        sleep(560);

        //Move straight towards OPPOSITE WALL
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(1800);

        //Turn right
        motorLeft.setPower(0.5);
        motorRight.setPower(0);
        sleep(600);

        //Move straight towards beacon
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(1350);

        //Turn left a little to stay away from the BLUE tape
       // motorLeft.setPower(0);
       // motorRight.setPower(0.5);
       // sleep(600);

        //Move straight towards beacon
       // motorLeft.setPower(0.5);
       // motorRight.setPower(0.5);
       // sleep(250);

        //Turn left a little to get towards the beacon
      /*  motorLeft.setPower(0);
        motorRight.setPower(0.5);
        sleep(1000);*/

        //Move straight towards beacon
       /* motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(400);*/

        ///Stop motors
        motorLeft.setPower(0);
        motorRight.setPower(0);

        //Color.RGBToHSV((sensorRGB.red() * 8), (sensorRGB.green() * 8), (sensorRGB.blue() * 8), hsvValues);
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8,hsvValues);

        // send the info back to driver station using telemetry function.
       // telemetry.addData("Clear", sensorRGB.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        //telemetry.addData("Green", sensorRGB.green());
        telemetry.addData("Blue ", colorSensor.blue());
      //  telemetry.addData("Hue", hsvValues[0]);

        if (colorSensor.blue()>= 1) {     //Check to see if the color is BLUE

            arm.setPosition(0.1);       //Assuming we are RED, we want to push the RIGHT button
        }
        else {
            arm.setPosition(0.9);       //Find the LEFT button to push
        }

        // Move forward and push the button


        /*if (!button.isPressed()) {  //Stop when touch sensor is pressed
            motorLeft.setPower(0.5);
            motorRight.setPower(0.5);
           // sleep(500);
        }
        else{
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }*/
        sleep(1000);
        claw.setPosition(1);

       /* motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(500);*/

        //claw.setPosition(0);

        //Stop motors in case robot still moving
        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(620);
        claw.setPosition(0);

        //HEAD TO THE MOUNTAIN!
        //Back up from the beacon
        motorLeft.setPower(-0.5);
        motorRight.setPower(-0.5);
        sleep(2800);

        //Turn right
        motorLeft.setPower(0.5);
        motorRight.setPower(0);
        sleep(600);


        //Forward to the ramp
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(1500);

        //Forward to the ramp
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(6000);

        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}

