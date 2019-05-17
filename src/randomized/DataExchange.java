package randomized;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Store information about current robot state.
enum Status {
	MOVING,
	ROTATINGLEFT,
	ROTATINGRIGHT,
	SHOOTING,
	UNRESOLVED,
}



//Exchange data between threads.
public class DataExchange {
	// At the start, robot is set to move.
		private Status STATE = Status.MOVING;
		private Lock lock;
		private boolean active;
		private int noShoot;
		
		
		public DataExchange() {
		  this.lock = new ReentrantLock();
		  this.noShoot = 0;
		  this.active = true;
		}
		
		public void setSTATE(Status state) {
			STATE = state;
		}
		
		public Status getSTATE() {
			return STATE;
		}
		
		public Lock getLock () {
			return this.lock;			
		}
		
		public void incrementShoot() {
			this.noShoot++;
		}
		
		public boolean outOfAmmo() {
			if(noShoot >= 3) {
				return true;
			}
			return false;
		}
		
		public boolean isActive () {
			return this.active;
		}
		
		public void inactivate () {
			this.active = false;
		}
		
}
