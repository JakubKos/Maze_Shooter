package randomized;

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
		
		public DataExchange() {
			
		}
		
		public void setSTATE(Status state) {
			STATE = state;
		}
		
		public Status getSTATE() {
			return STATE;
		}
}
