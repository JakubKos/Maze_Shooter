package randomized;


import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.util.concurrent.locks.Lock;

import lejos.hardware.Sound;

public class Mover extends Thread{
	DataExchange DEObj;
	 private static EV3LargeRegulatedMotor LeftMotor;
	 private static EV3LargeRegulatedMotor RightMotor;
	 //How many degrees one motor has to rotate for the robot to rotate 1 degree 
	 private static final double ROTATE_DEGREES_FACTOR = 11.3; 

	 
	 public Mover(DataExchange DE) {
		DEObj = DE; 
		LeftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		RightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	 }

	 public void moveForward() {

		 LeftMotor.setSpeed(200);
		 RightMotor.setSpeed(200);
		 LeftMotor.forward();
		 RightMotor.forward();
	 }
	 
	 public static void stopMotors() {
		 // stop infinity forward movement
		 LeftMotor.flt();
		 RightMotor.flt();
	 }
	 
	 public void closeMotors() {
		 if (LeftMotor != null) {
			 LeftMotor.close();
		 }
		 if (RightMotor != null) {
			 RightMotor.close();
		 } 
	 }
	 
	 public void turnRight(int degrees) {
		 LeftMotor.setSpeed(200);
		 LeftMotor.rotate((int) (degrees * ROTATE_DEGREES_FACTOR));
		 Sound.beep();
	 }
	 
	 public void turnLeft(int degrees) {
		 RightMotor.setSpeed(200);
		 RightMotor.rotate((int) (degrees * ROTATE_DEGREES_FACTOR));
		 Sound.beep();
	 }
	 
	 public void run() {
		 //Infinite task
		 Lock lck = DEObj.getLock();
			 while(DEObj.isActive()) {
				 lck.lock();
				 try {
					 if(DEObj.getSTATE() == Status.MOVING) {
						 moveForward();
					 } else if(DEObj.getSTATE() == Status.ROTATINGRIGHT) {
						 turnRight(90);
						 DEObj.setSTATE(Status.MOVING);
					 } else if(DEObj.getSTATE() == Status.ROTATINGLEFT) {
						 turnLeft(90);
						 DEObj.setSTATE(Status.MOVING);	
					 } else if(DEObj.getSTATE() == Status.SHOOTING) {
						 stopMotors();
					 }
				 } finally {
					 lck.unlock();
				 }
				 
			 } 
	 }
}
