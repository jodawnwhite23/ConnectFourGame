package connect.four.player;

public class PlayerQuitException extends IllegalStateException{
    public PlayerQuitException(String message) {
	super(message);
    }
    public PlayerQuitException() {
	super("Player quit.");
    }
}
