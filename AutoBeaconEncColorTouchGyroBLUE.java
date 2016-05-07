package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Leah on 11/18/2015.
 *
 * This program uses the following sensors:
 * Modern Robotics Color Sensor ("mr")
 * Modern Robotics Touch Sensor ("mt")
 */
public class AutoBeaconEncColorTouchGyroBLUE extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor colorSensor;
    Servo arm;
    Servo claw;
    TouchSensor button;

    final static int ENCODER_CPR = 1440; // counts
    final static double GEAR_RATIO = 1; //gear ratio
    final static int WHEEL_DIAMETER = 4; // Diameter of the wheel in inches


    public void runOpMode() throws InterruptedException {

        motorRight = hardwareMap.dcMotor.get("RightMotor");
        motorLeft = hardwareMap.dcMotor.get("LeftMotor");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        colorSensor = hardwareMap.colorSensor.get("mr");
        arm = hardwareMap.servo.get("beacon");
        claw = hardwareMap.servo.get("servo_6");
        button = hardwareMap.touchSensor.get("mt");

        double target;
        int fromWall = 54;
        int turn1 = 14;
        int alongTape = 43;
        int turn2 = 10;
        int toWall = 33;    //int toWall = 32;
        int backupToTape = 32;
        int turnBack = 21;
        int turnToRamp = 10;
        int toRamp = 45;

        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;


        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        //Sets the encoders to 0
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        //This just means that the motor controllers themselves will not make internal use of the encoder values.
        // In this mode, the motors work exactly as you're using them now (in fact, this is what they default
        // to if you don't set them to anything).  In spite of the name, in this mode you can read the encoder values
        // in your program and act based on them.

       // while (opModeIsActive()) {
         //   if (button.isPressed()) {
                //Stop the motors if the touch sensor is pressed
           //     motorLeft.setPower(0);
             //   motorRight.setPower(0);
           // } else {
                //Keep driving if the touch sensor is not pressed
                //    motorLeft.setPower(0.5);
                //    motorRight.setPower(0.5);
          //  }

            telemetry.addData("isPressed", String.valueOf(button.isPressed()));

            motorLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            motorRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

            //so that the change in modes can happen...
            waitOneFullHardwareCycle();

            arm.setPosition(0.5);  //Turn arm vertically

            //Move straight towards line            MOVE #1
            double ROTATIONS = fromWall / CIRCUMFERENCE;
            double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
            target = motorLeft.getCurrentPosition() + COUNTS;

            motorLeft.setTargetPosition((int) COUNTS);
            motorRight.setTargetPosition((int) COUNTS);


            motorLeft.setPower(0.5);
            motorRight.setPower(0.5);

            while (motorLeft.getCurrentPosition() < COUNTS) {
                waitOneFullHardwareCycle();
            }
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
            sleep(500); // wait a half a second for the motors to stop


            //Turn right                     MOVE #2
            ROTATIONS = turn1 / CIRCUMFERENCE;
            COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
            target = motorLeft.getCurrentPosition() + COUNTS;

            motorLeft.setTargetPosition((int) COUNTS);
            motorRight.setTargetPosition((int) COUNTS);

            motorRight.setPower(0);
            motorLeft.setPower(0.5);

            while (motorLeft.getCurrentPosition() < target) {
                waitOneFullHardwareCycle();
            }
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
            sleep(500); // wait a half a second for the motors to stop


            //Move straight ALONG THE TAPE          MOVE #3
            ROTATIONS = alongTape / CIRCUMFERENCE;
            COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
            target = motorRight.getCurrentPosition() + COUNTS;


            motorLeft.setPower(0.5);
            motorRight.setPower(0.5);

            while (motorRight.getCurrentPosition() < target) {
                waitOneFullHardwareCycle();
            }
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
            sleep(500); // wait a half a second for the motors to stop

            //Turn right TOWARDS THE BEACON               MOVE #4
            ROTATIONS = turn2 / CIRCUMFERENCE;
            COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
            target = motorLeft.getCurrentPosition() + COUNTS;

            motorRight.setPower(0);
            motorLeft.setPower(0.5);

            while (motorLeft.getCurrentPosition() < target) {
                waitOneFullHardwareCycle();
            }
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
            sleep(500); // wait a half a second for the motors to stop


            //Move straight towards beacon          MOVE #5
            ROTATIONS = toWall / CIRCUMFERENCE;
            COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
            target = motorRight.getCurrentPosition() + COUNTS;


                motorLeft.setPower(0.5);
                motorRight.setPower(0.5);
                while (motorRight.getCurrentPosition() < target) {
                    waitOneFullHardwareCycle();

                    if(button.isPressed()) {
                        //Stop the motors if the touch sensor is pressed
                        motorLeft.setPower(0);
                        motorRight.setPower(0);
                    }
                }

                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop


                //Color.RGBToHSV((sensorRGB.red() * 8), (sensorRGB.green() * 8), (sensorRGB.blue() * 8), hsvValues);
                Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

                // send the info back to driver station using telemetry function.
                // telemetry.addData("Clear", sensorRGB.alpha());
                telemetry.addData("Red  ", colorSensor.red());
                //telemetry.addData("Green", sensorRGB.green());
                telemetry.addData("Blue ", colorSensor.blue());
                //  telemetry.addData("Hue", hsvValues[0]);

                if (colorSensor.blue() >= 1) {     //Check to see if the color is BLUE

                    arm.setPosition(0.1);       //Assuming we are RED, we want to push the RIGHT button
                } else if (colorSensor.red() >= 1) {
                     arm.setPosition(0.9);       //Find the LEFT button to push
                }


                sleep(1000);
                claw.setPosition(1);


                //Stop motors in case robot still moving
                motorLeft.setPower(0);
                motorRight.setPower(0);

                sleep(1000);
                claw.setPosition(0);

                //HEAD TO THE MOUNTAIN!
                //Back up from the beacon           MOVE #5 IN REVERSE

                ROTATIONS = backupToTape / CIRCUMFERENCE;
                COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
                target = motorRight.getCurrentPosition() - COUNTS;

                motorLeft.setPower(-0.5);
                motorRight.setPower(-0.5);
                while (motorRight.getCurrentPosition() > target) {
                    waitOneFullHardwareCycle();
                }
                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop


                //Turn back from whence we came                     MOVE #4 IN REVERSE;
                ROTATIONS = turnBack / CIRCUMFERENCE;
                COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
                target = motorRight.getCurrentPosition() - COUNTS;

                motorRight.setPower(-0.5);
                motorLeft.setPower(0);

                while (motorRight.getCurrentPosition() > target) {
                    waitOneFullHardwareCycle();
                }
                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop


                //MOVE towards ramp
                ROTATIONS = toRamp / CIRCUMFERENCE;
                COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
                target = motorRight.getCurrentPosition() + COUNTS;

                motorLeft.setPower(0.5);
                motorRight.setPower(0.5);

                while (motorRight.getCurrentPosition() < target) {
                    waitOneFullHardwareCycle();
                }
                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop

                //Turn facing the ramp
                ROTATIONS = (turnToRamp + 15) / CIRCUMFERENCE;
                COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
                target = motorLeft.getCurrentPosition() + COUNTS;

                motorRight.setPower(0.5);
                motorLeft.setPower(0);

                while (motorLeft.getCurrentPosition() < target) {
                    waitOneFullHardwareCycle();
                }
                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop


                //Move backwards straight ALONG THE TAPE                  HALF MOVE #3 IN REVERSE
  /*      ROTATIONS = (alongTape/2) /CIRCUMFERENCE;
        COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        target = motorRight.getCurrentPosition() + COUNTS;


        motorLeft.setPower(-0.5);
        motorRight.setPower(-0.5);

        while (motorRight.getCurrentPosition() > target) {
            waitOneFullHardwareCycle();
        }
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);
        sleep(500); // wait a half a second for the motors to stop

        //TURN LEFT
        //Turn left TOWARDS THE BEACON               MOVE #4
        ROTATIONS = turn2 /CIRCUMFERENCE;
        COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        target = motorRight.getCurrentPosition() + COUNTS;

        motorLeft.setPower(0);
        motorRight.setPower(0.5);

        while (motorRight.getCurrentPosition() < target) {
            waitOneFullHardwareCycle();
        }
        motorLeft.setPower(0.0);
        motorRight.setPower(0.0);
        sleep(500); // wait a half a second for the motors to stop

*/
                //Forward to the ramp
                ROTATIONS = (toRamp + 15) / CIRCUMFERENCE;
                COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
                target = motorLeft.getCurrentPosition() + COUNTS;


                motorLeft.setPower(0.7);
                motorRight.setPower(0.7);

                while (motorLeft.getCurrentPosition() < target) {
                    waitOneFullHardwareCycle();
                }
                motorLeft.setPower(0.0);
                motorRight.setPower(0.0);
                sleep(500); // wait a half a second for the motors to stop


                motorLeft.setPower(0);
                motorRight.setPower(0);

                telemetry.addData("Counts", COUNTS);
                telemetry.addData("Motor Rotations", ROTATIONS);
                telemetry.addData("Left Position", motorLeft.getCurrentPosition());
                telemetry.addData("Right Position", motorRight.getCurrentPosition());
            }

        }


