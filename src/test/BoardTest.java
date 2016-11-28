package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import connect.four.board.Board;
import connect.four.board.ColumnFullException;
import connect.four.board.ReadableBoard;
import connect.four.player.ComputerPlayer;
import connect.four.player.ConsolePlayer;
import connect.four.player.Player;

public class BoardTest {
    private static Board board;
    private static ConsolePlayer console;
    private static ComputerPlayer computer;

    /**
     * Creates a new Board
     */
    @Before
    public void initialiseTest() {
        board = new Board(new ReadableBoard() {
            @Override
            public Player whoPlayed(int x, int y) {
                return null;
            }//end whoPlayed

            @Override
            public int getWidth() {
                return 7;
            }//end getWidth

            @Override
            public int getHeight() {
                return 6;
            }//end getHeight

            @Override
            public int getColumnHeight(int x) {
                return 0;
            }//end getColumnHeight

            @Override
            public int getMoveCount() {
                return 0;
            }//end getMoveCount
        });

        /**
         * Create two new Players to populate the board
         */
        console = createConsolePlayer();
        computer = createComputerPlayer();
    }//end initializeTest

    @Test
    public void testPlay() {
        /**
         * Case 1, x=0
         */
        board.play(0, console);
        Assert.assertEquals("First slot in first column must be filled", console, board.whoPlayed(0, 0));
        Assert.assertEquals("Only one move should be registered to the board", 1, board.getMoveCount());

        /**
         * Case 2, x=1
         */
        board.clear();
        board.play(0, console);
        board.play(1, computer);
        board.play(1, computer);
        board.play(2, console);
        board.play(5, computer);
        board.play(5, console);
        board.play(1, console); //The changing play
        Assert.assertEquals("Changing play must be filled", console, board.whoPlayed(1, 2));
        Assert.assertEquals("Only 7 moves should be registered to the board", 7, board.getMoveCount());
    }//end testPlay

    /**
     * Case 3, testFailedPlay
     */
    @Test(expected = ColumnFullException.class)
    public void testFailedPlay() {
        /**
         * Case 3, x=3
         */
        board.play(3, console);
        board.play(3, computer);
        board.play(3, console);
        board.play(3, computer);
        board.play(3, console);
        board.play(3, computer);
        //Column should be full here
        board.play(3, console); //The changing play
    }//end testFailedPlay

    @Test
    public void testGetColumnHeight() {
        /**
         * Case 0, x=0
         */
        board.play(0, computer);
        board.play(1, console);
        board.play(1, console);
        board.play(1, console);
        board.play(2, computer);
        board.play(2, console);
        Assert.assertEquals("Height of column 0 should be 1", 1, board.getColumnHeight(0));

        /**
         * Case 1, x=1
         */
        board.clear();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, console);
        board.play(1, console);
        board.play(2, computer);
        board.play(2, console);
        Assert.assertEquals("Height of column 1 should be 3", 3, board.getColumnHeight(1));

        /**
         * Case 2, x=2
         */
        board.clear();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, console);
        board.play(1, console);
        board.play(2, computer);
        board.play(2, console);
        Assert.assertEquals("Height of column 2 should be 2", 2, board.getColumnHeight(2));

        /**
         * Case 3, x=3
         */
        board.clear();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, console);
        board.play(1, console);
        board.play(2, computer);
        board.play(2, console);
        Assert.assertEquals("Height of column 3 should be 0", 0, board.getColumnHeight(3));

        /**
         * Case 4, x=4
         */
        board.clear();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, console);
        board.play(1, console);
        board.play(4, computer);
        board.play(4, console);
        Assert.assertEquals("Height of column 4 should be 2", 2, board.getColumnHeight(4));
       
        /**
         * Case 5, x=5
         */        
        board.clear();
        Assert.assertEquals("Height of column 5 should be 0", 0, board.getColumnHeight(5));

        /**
         * Case 6, x=6
         */
        board.clear();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, computer);
        board.play(3, computer);
        board.play(3, computer);
        board.play(3, computer);
        board.play(3, console);
        board.play(3, computer);
        board.play(3, console);
        board.play(5, console);
        board.play(6, console);
        board.play(6, computer);
        Assert.assertEquals("Height of column 6 should be 6", 2, board.getColumnHeight(6));
    }//end testGetColumnHeight

    @Test
    public void testClear() {
        /**
         * Case 1
         */
        board.play(0, console);
        board.clear();
        Assert.assertEquals("Board must be empty", 0, board.getMoveCount());

        /**
         * Case 2
         */
        console = createConsolePlayer();
        board.play(0, computer);
        board.play(1, console);
        board.play(1, computer);
        board.play(4, console);
        board.play(4, computer);
        board.play(6, console);
        board.play(5, computer);
        board.play(6, console);
        board.play(4, console);
        board.play(2, computer);
        board.play(1, console);
        board.clear();
        Assert.assertEquals("Must be an empty board.", 0, board.getMoveCount());
        
        /**
         * Case 3
         */
        console = createConsolePlayer();
        computer = createComputerPlayer();
        board.clear();
        Assert.assertEquals("Board must be empty", 0, board.getMoveCount());
    }
    
    @Test
	public void whoPlayedTest() {
		Board aBoard = new Board(6,7);
		Player aPlayer = new ConsolePlayer("Joey");
		Player bPlayer = new ConsolePlayer("Jordan");
		Player cPlayer = new ComputerPlayer(3);
		
		// Player makes several moves and check results
		aBoard.play(1, aPlayer);
		aBoard.play(1, bPlayer);
		aBoard.play(1, aPlayer);
		aBoard.play(1, bPlayer);
		
		//Check results of Player
		assertTrue(aBoard.whoPlayed(1, 0).getName().equals("Joey"));
		assertTrue(aBoard.whoPlayed(1, 1).getName().equals("Jordan"));
		assertTrue(aBoard.whoPlayed(1, 2).getName().equals("Joey"));
		assertTrue(aBoard.whoPlayed(1, 3).getName().equals("Jordan"));
		assertTrue(cPlayer.getName().equals("Computer"));
	}//end whoPlayedTest
    
    @Test
    public void getMoveCountTest() {
		Board aBoard = new Board(6,7);
		Player aPlayer = new ConsolePlayer("Barton");
		Player cPlayer = new ComputerPlayer();
		
		//Initially, board moves should equal zero
		assertTrue(aBoard.getMoveCount() == 0);
		
		//Player makes 3 moves
		aBoard.play(2, aPlayer);
		aBoard.play(2, aPlayer);
		aBoard.play(2, aPlayer);
		
		//Board moves should equal 3
		assertTrue(aBoard.getMoveCount() == 3);
		
		//Computer makes move and check results
		aBoard.clear();
		aBoard.play(1, aPlayer);
		cPlayer.performPlay(aBoard);
		assertTrue(aBoard.getMoveCount()== 2);
		
		aBoard.clear();
		cPlayer.performPlay(aBoard);
		assertTrue(aBoard.getMoveCount() == 1);
		
	}//end getMoveCountTest

    private ConsolePlayer createConsolePlayer() {
        return new ConsolePlayer("Human Player");
    }//end createConsolePlayer

    private ComputerPlayer createComputerPlayer() {
        return new ComputerPlayer();
    }//end createComputerPlayer
    
}//end BoardTest
