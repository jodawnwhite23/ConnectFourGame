package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connect.four.Game;
import connect.four.ScoreChart;
import connect.four.ScoreChart.Listener;
import connect.four.board.Board;
import connect.four.board.ReadWritableBoard;
import connect.four.board.ReadableBoard;
import connect.four.board.TestableBoard;
//import connect.four.gui.GUIPlayer;
import connect.four.player.ConsolePlayer;
import connect.four.player.Player;

public class GameTest {
	private Game gameObj = null;
	private Player p1 = null;
	private Player p2 = null;
	private Board b = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Set up Board Object
		b = new Board(7, 6);
		
		// Set up Player Objects
		p1 = new ConsolePlayer("Player 1");
		p2 = new ConsolePlayer("Player 2");
		
		// Set up Game Object
		gameObj = new Game(new Player[] {p1, p2}, b, 4);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetCurrentPlayer_IntialValues() {
		assertTrue(gameObj.getCurrentPlayer() == p1);
	}

	@Test
	public void testGetScore_InitialValues() {
		assertTrue(gameObj.getScore(p1) == 0);
		assertTrue(gameObj.getScore(p2) == 0);
	}
	
	@Test
	public void testGetPlayers() {
		List<Player> list = gameObj.getPlayers();
		assertTrue(list != null);
		assertTrue(list.contains(p1));
		assertTrue(list.contains(p2));
	}
	
	@Test
	public void testListoners() {
		Listener listenerObj = new Listener() {
			
			@Override
			public void gameOver(Player winner, ScoreChart scores, ReadableBoard end) {
			}
		};
		gameObj.registerListener(listenerObj);
		gameObj.unregisterListener(listenerObj);
	}

	@Test
	public void testDetectWinner_HorizontalWin() {
		TestableBoard testBoard = new TestableBoard(7, 6);

		testBoard.setPlayerPiece(0, 0, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(0, 1, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(0, 2, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(0, 3, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == p1);
	}

	@Test
	public void testDetectWinner_VerticalWin() {
		TestableBoard testBoard = new TestableBoard(7, 6);

		testBoard.setPlayerPiece(0, 0, p2);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(1, 0, p2);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(2, 0, p2);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(3, 0, p2);
		assertTrue(Game.detectWinner(testBoard, 4) == p2);
	}

	@Test
	public void testDetectWinner_DiagonalWin() {
		TestableBoard testBoard = new TestableBoard(7, 6);

		testBoard.setPlayerPiece(0, 0, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(1, 1, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(2, 2, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == null);
		testBoard.setPlayerPiece(3, 3, p1);
		assertTrue(Game.detectWinner(testBoard, 4) == p1);
	}
	
	@Test
	public void testGetBoard() {
		assertTrue(gameObj.getBoard() == b);
	}
}