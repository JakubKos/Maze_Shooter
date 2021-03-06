package randomized;

import java.util.concurrent.locks.Lock;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TerroristDetector extends Thread {
	DataExchange DEObj;
	private EV3IRSensor ir1;
	private SampleProvider sp;
	private float [] sample;
	private float terroristDistance = 30f;
	private EV3MediumRegulatedMotor m1;
	
	
	public TerroristDetector(DataExchange DE) {
		DEObj = DE;
		ir1 = new EV3IRSensor(SensorPort.S4);
		sp = ir1.getDistanceMode();
    	sample = new float[sp.sampleSize()];
    	m1 = new EV3MediumRegulatedMotor(MotorPort.A);
	}
	
	public void shoot() {
		//rotate medium motor to shoot
		DEObj.incrementShoot();
		m1.setSpeed(1080);
		m1.setAcceleration(6000);
		m1.rotateTo(1080);
		m1.resetTachoCount();
	}
	
	float distanceValue = 0;
	
	public void run() {
		Lock lck = DEObj.getLock();
		while(DEObj.isActive()) {
			//continually get and check distance
			sp.fetchSample(sample, 0);
            distanceValue = sample[0];
            if(distanceValue < terroristDistance) {
            	lck.lock();
            	try {
            		DEObj.setSTATE(Status.UNRESOLVED);
                	Mover.stopMotors();
                	DEObj.setSTATE(Status.SHOOTING);
                	//shoot 3 times
                	shoot();
                	if(!DEObj.outOfAmmo()) {
                		DEObj.setSTATE(Status.MOVING);
                	} else {
                		DEObj.inactivate();
                	}
            	} finally {
					lck.unlock();
				}
            }
		}
	}

}
