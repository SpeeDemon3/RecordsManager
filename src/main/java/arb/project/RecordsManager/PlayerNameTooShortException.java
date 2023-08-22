package arb.project.RecordsManager;

public class PlayerNameTooShortException extends Exception {
	
	private String name;
	
	public PlayerNameTooShortException(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "El nombre del jugador (" + name + ") es demasiado corto";
	}
	
}
