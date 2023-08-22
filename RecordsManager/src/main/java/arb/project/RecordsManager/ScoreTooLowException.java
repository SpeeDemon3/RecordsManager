package arb.project.RecordsManager;

public class ScoreTooLowException extends Exception {

	private String name;
	private int score;
	
	public ScoreTooLowException(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return "El usuario " + name + " tiene menos puntos ("
				+ score + ") de los requeridos.";
	}
	
}
