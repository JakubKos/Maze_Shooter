package randomized;

public class mainer {
	private static DataExchange DE;
	private static Mover MVObj;
	private static ColorDetector ColObj;
	private static TerroristDetector ShootObj;
	
	public static void main(String[] args) {

		DE = new DataExchange();
		MVObj = new Mover(DE);
		ColObj = new ColorDetector(DE);
		ShootObj = new TerroristDetector(DE);
		ColObj.start();
		MVObj.start();
		ShootObj.start();
	}

}
              