
package connect.four;

import connect.four.player.Player;
import connect.four.player.ConsolePlayer;
import connect.four.board.ReadableBoard;
import connect.four.board.ReadWritableBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Game implements ScoreChart {
    Player[] m_players;
    int[] m_scores;
    List<ScoreChart.Listener> m_listeners;
    ReadWritableBoard m_board;
    int m_inRow;
    int m_currentPlayer;
    
    public Game(Player[] players, ReadWritableBoard board, int inRow) {
        m_players = Arrays.copyOf(players, players.length);
        m_scores = new int[players.length];
        m_listeners = new ArrayList<ScoreChart.Listener>();
        m_board = board;
        m_inRow = inRow;
    }
    public void start() {
        int first = (new Random()).nextInt(m_players.length);
        performPlay(first);
    }
    @Override public void registerListener(ScoreChart.Listener l) {
        m_listeners.add(l);
    }
    @Override public void unregisterListener(ScoreChart.Listener l) {
        m_listeners.remove(l);
    }
    @Override public List<Player> getPlayers() {
        return Arrays.asList(m_players);
    }
    @Override public int getScore(Player p) {
        int pos = -1;
        int l = m_players.length;
        for (int i = 0; i != l; ++i) {
            if (m_players[i] == p) pos = i;
        }
        return m_scores[pos];
    }
    void performPlay(final int player) {
        m_currentPlayer = player;
        ReadWritableBoard controlledBoard = new ReadWritableBoard() {
            //boolean played;
            @Override public Player whoPlayed(int x, int y) {
                return m_board.whoPlayed(x, y);
            }
            @Override public void play(int x, Player p) {         	
                m_board.play(x, p);
                Player win = detectWinner(m_board, m_inRow);
                if (win != null) {
                    m_scores[player] += 1;
                    //Begin code modified by Group 22
                    clearScreen();
                    System.out.println(win.getName() + "Won!!!");
                    printPlayerPieceAssociations();
                    System.out.println("Final board state: ");
                    printBoard(m_board);
                    printScores();
                  //End code modified by Group 6
                    m_board.clear();
                    playAgainPrompt();//code modified by Group 6
                    performPlay(player);
		} else if (m_board.getMoveCount() == m_board.getWidth()*m_board.getHeight() ) {
					//Begin code modified by Group 6:
		        	clearScreen();
		        	System.out.println("Draw! Nobody wins.");
		        	printPlayerPieceAssociations();
		        	System.out.println("Final board state:");
		            printBoard(m_board);
		            printScores();
		            //End of modification
                    m_board.clear();
                    playAgainPrompt(); //code modified by Group 22
                    performPlay((player+1) % m_players.length);
                } else {
                    performPlay((player+1) % m_players.length);
                }
            }
            @Override public void clear() {
                m_board.clear();
            }
            @Override public int getWidth() {
                return m_board.getWidth();
            }
            @Override public int getHeight() {
                return m_board.getHeight();
            }
	    @Override public int getColumnHeight(int x) {
		return m_board.getColumnHeight(x);
	    }
	    @Override public int getMoveCount() {
		return m_board.getMoveCount();
	    }
        };
        
        //Begin code modified by Group 6:
        clearScreen();
        printPlayerPieceAssociations();
        System.out.println(m_players[player].getName() + "'s turn!");
        System.out.println();
        printBoard(m_board);
        //End code modified by Group 6
        m_players[player].performPlay(controlledBoard);
    }
    
    public Player getCurrentPlayer(){
            return m_players[m_currentPlayer];
    }

    public int getInRow() {
	return m_inRow;
    }

    public ReadableBoard getBoard() {
	return m_board;
    }

    public static Player detectWinner(ReadableBoard board, int inRow) {
        int l = board.getWidth();
        int m = board.getHeight();
        for (int i = 0; i != l; ++i) {
            Player possible = null;
            int found = 0;
            for (int j = 0; j != m; ++j) {
                if (board.whoPlayed(i, j) == possible && possible != null) {
                    found += 1;
                } else {
                    found = 1;
                    possible = board.whoPlayed(i, j);
                }
                if (found == inRow) {
                    return possible;
                }
            }
        }
        for (int i = 0; i != m; ++i) {
            Player possible = null;
            int found = 0;
            for (int j = 0; j != l; ++j) {
                if (board.whoPlayed(j, i) == possible && possible != null) {
                    found += 1;
                } else {
                    found = 1;
                    possible = board.whoPlayed(j, i);
                }
                if (found == inRow) {
                    return possible;
                }
            }
        }
	for (int i = -l; i != l; ++i) {
            Player possible = null;
            int found = 0;
	    for (int j = 0; j != m; ++j) {
		int k = j+i;
		if (k >= 0 && k < l) {
                    if (board.whoPlayed(k, j) == possible && possible != null) {
                        found += 1;
                    } else {
                        found = 1;
                        possible = board.whoPlayed(k, j);
                    }
                    if (found == inRow) {
                        return possible;
                    }
		}
	    }
	}
	for (int i = -l; i != l; ++i) {
            Player possible = null;
            int found = 0;
	    for (int j = 0; j != m; ++j) {
		int k = j+i;
		if (k >= 0 && k < l) {
                    if (board.whoPlayed(l-k-1, j) == possible && possible != null) {
                        found += 1;
                    } else {
                        found = 1;
                        possible = board.whoPlayed(l-k-1, j);
                    }
                    if (found == inRow) {
                        return possible;
                    }
		}
	    }
	}
        return null;
    }
    
    /**
     * Prompt the player(s) and ask them if they
     * want to play again. Modification added 11-29-2016
     */
    private void playAgainPrompt(){
    	System.out.println();
        System.out.println("Would you like to play again?");
        System.out.println("(Enter 0 for quit and 1 for play again)");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int x = -1;
        while (x < 0 || x > 1) {
            try {
                System.out.print("Enter your selection: ");
                x = Integer.parseInt(stdin.readLine());
            } catch (IOException e) {
                // loop again.
            } catch (NumberFormatException e) {
                // loop again.
            }
        }
        if(x == 0)
            System.exit(0);
        else
        	System.out.println("Playing again!");
    }
    
    /**
     * Print the board. Modification 11-29-2016
     * Taken from class ConsolePlayer (where it is called dumpBoard)
     * @param board
     */
    private void printBoard(ReadableBoard board) {
        int width = board.getWidth();
        int height = board.getHeight();

        for (int i = height-1; i != -1; --i) {
            for (int j = 0; j != width; ++j) {
                Player played = board.whoPlayed(j, i);
                System.out.print(
                    played == m_players[0] ? "@ " :
                    played == null ? "O " : "X "
                );
            }
            System.out.println();
        }
        for (int i = 0; i != width-1; ++i) {
            System.out.print("--");
        }
        System.out.print("-");
        System.out.println();
        for (int i = 0; i != width; ++i) {
            System.out.print(i+1 + " ");
        }
        System.out.println();
    }
    
    /**
     * Helper method to print the scores. Modification added
     * 11-29-2016
     */
    private void printScores(){
    	System.out.println("Scores:");
        System.out.println(m_players[0].getName() + ": " + getScore(m_players[0]));
        System.out.println(m_players[1].getName() + ": " + getScore(m_players[1]));
    }
    
    /**
     * Helper method to clear the screen by 
     * printing many newlines. Modification 11-29-2016
     */
    private void clearScreen(){
    	for(int i = 0; i < 50; i++)
    		System.out.println();
    }
    
    /**
     * Helper method to print which pieces belong to which players.
     */
    private void printPlayerPieceAssociations() {
        System.out.println(m_players[0].getName() + ": @");
    	System.out.println(m_players[1].getName() + ": X");
    	System.out.println();
    }

}
