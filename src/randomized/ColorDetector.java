package randomized;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorDetector extends Thread{
	DataExchange DEObj;
	private EV3ColorSensor colour;
    
	public ColorDetector(DataExchange DE) {
		DEObj = DE;
		colour = new EV3ColorSensor(SensorPort.S3);
	}
	
	public void run() {
		colour.setFloodlight(true);
			while(true) {
				int color = colour.getColorID();
				if(color == Color.RED) {
					Sound.twoBeeps();
					// force robot to stop
					DEObj.setSTATE(Status.UNRESOLVED);
					Mover.stopMotors();
					DEObj.setSTATE(Status.ROTATINGLEFT);
				} 
				else if(color == Color.GREEN) {
					Sound.twoBeeps();
					// force robot to stop
					DEObj.setSTATE(Status.UNRESOLVED);
					Mover.stopMotors();
					DEObj.setSTATE(Status.ROTATINGRIGHT);	
				}
			}
	}
}
