
package connect.four.board;

import connect.four.player.Player;
import java.util.Arrays;

/**
 * A representation of a Connect-Four board. With methods for getting the
 * contents of the board and making moves.
 */
public class Board implements ReadWritableBoard {
    Player[][] m_contents;
    int m_moveCount;
    public Board(int width, int height) {
        m_contents = new Player[width][height];
        m_moveCount = 0;
    }
    
    /**
     * @param Constructor added for testing purposes by Scott and Greg
     * */
    public Board(Player[][] contents) {
        
    	m_contents = new Player[contents.length][contents[0].length];
        
        for(int i = 0; i < contents.length; i++){
        	for(int j = 0; j < contents[i].length; j++){
        		m_contents[i][j] = contents[i][j];
        	}
        }        
        m_moveCount = 0;
    }
/**=========End added constructor =======================================*/
    
    /**
     * Performs a deep copy of an existing borad into a new board
     * @param copy An existing board to be deep copied
     */
    public Board(ReadableBoard copy) {
        if (copy instanceof Board) {
            Board copyB = (Board) copy;
            m_moveCount = copyB.m_moveCount;
            int l = copyB.m_contents.length;
            int m = copyB.m_contents[0].length;
            m_contents = new Player[l][m];
            for (int i = 0; i != l; ++i) {
                m_contents[i] = Arrays.copyOf(copyB.m_contents[i], m);
            }
        } else {
            int l = copy.getWidth();
            int m = copy.getHeight();
            m_contents = new Player[l][m];
            m_moveCount = copy.getMoveCount();
            for (int i = 0; i != l; ++i) {
                for (int j = 0; j != m; ++j) {
                    m_contents[i][j] = copy.whoPlayed(i, j);
                }
            }
        }
    }
    
    /**
     * Determine which player the token at the given position belongs to
     * @param x The column in which the piece exists
     * @param y The row in which the piece exists
     * @return The player which corresponds to the piece at the requested position
     */
    public @Override Player whoPlayed(int x, int y) {
        return m_contents[x][y];
    }
    
    /**
     * Returns the width of the game board
     * @return Board width in number of slots
     */
    public @Override int getWidth() {
        return m_contents.length;
    }
    
    /**
     * Returns the game board height
     * @return Board height in number of slots
     */
    public @Override int getHeight() {
        return m_contents[0].length;
    }
    
    /**
     * Place a token in the selected column
     * @param x The column to place the token into
     * @param p The player who will own the inserted token
     */
    public @Override void play(int x, Player p) {
        int y = getColumnHeight(x);
        if (y == m_contents[x].length) {
            throw new ColumnFullException();
        }
        m_contents[x][y] = p;
        m_moveCount += 1;
    }
    
    /**
     * Returns the number of tokens in the specified column
     * @param x The column to be searched for a number of tokens within it
     * @return The number of tokens in the requested column
     */
    public @Override int getColumnHeight(int x){
        int y = 0;
	int l = m_contents[0].length;
        while (y != l && m_contents[x][y] != null) {
            y += 1;
        }
        return y;
    }
    
    /**
     * Empties all stored tokens in the game board
     */
    public @Override void clear() {
        int l = m_contents.length;
        int m = m_contents[0].length;
        for (int i = 0; i != l; ++i) {
            m_contents[i] = new Player[m];
        }
	m_moveCount = 0;
    }
    
    /**
     * Gets the number of moves that have been played on this board
     * @return The total number of moves that have been played on this board
     */
    public @Override int getMoveCount() {
        return m_moveCount;
    }
}
