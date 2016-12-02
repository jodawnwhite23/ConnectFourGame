
package connect.four;

import java.io.*;
import connect.four.player.*;
import connect.four.board.*;


public class ConnectFour {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		final String QUIT = "quit";
		boolean playing = true;
		do {
			String p1Name=null;
			// Check for non empty and non null input
			do {
				System.out.print("Player's name: ");
				p1Name = stdin.readLine();
				if (p1Name.equals(QUIT))
					playing = false;
				else if (p1Name == null || p1Name.isEmpty())
					System.out.println("Please provide a name");
			} while (p1Name == null || p1Name.isEmpty() && playing);
			ConsolePlayer p1 = new ConsolePlayer(p1Name);
			String gameMode;
			Player p2=null;
			while (p2 == null && playing) {
				System.out.print("How many players, 1 or 2 (default 1)?");
				gameMode = stdin.readLine();
				if (gameMode.equals("1") || gameMode.isEmpty()) {
				    p2 = new ComputerPlayer();
				} else if (gameMode.equals("2")) {
					String p2Name=null;
					while ((p2Name == null || p2Name.isEmpty()) && playing) {
						System.out.print("Other player's name: ");
						p2Name = stdin.readLine();
						p2 = new ConsolePlayer(p2Name);
						if (p2Name == null || p2Name.isEmpty())
							System.out.println("Please provide a name");
						else if (p2Name.equals(QUIT))
							playing=false;
					}
				} else if (gameMode.equals(QUIT))
					playing = false;
				else
					System.out.println("Please provide a valid game mode");
			}
			if (playing){
				Game game = new Game(new Player[] {p1, p2}, new Board(7, 6), 4);
				game.registerListener(p1);
				if (p2 instanceof ScoreChart.Listener) game.registerListener((ScoreChart.Listener)p2);
				try {
					game.start();
				} catch (IllegalStateException ex){
					// end game
				}
			} else
				System.out.println("Exiting now");
		} while(playing);
    }
	
}
