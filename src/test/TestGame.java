package test;

import static org.junit.Assert.*;

import org.junit.Test;

import connect.four.Game;
import connect.four.board.*;
import connect.four.player.*;

public class TestGame {

	@Test
	public void testDetectWinner() {
		
		//Variable that controls how many game chips must be in a line to win
		int inRow = 4;
		
		//Instantiate the console players:
		ConsolePlayer symX = new ConsolePlayer("X");
		ConsolePlayer symO = new ConsolePlayer("O");
		
		//Setup the six board configurations:
		ConsolePlayer[][] board1Config = {{ symO, null, null, null, null, null }, //1
										  { symO, symO, symO, null, null, null }, //2
										  { symX, symX, symO, null, null, null }, //3
							/*Bottom*/    { symO, symX, symO, symO, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board2Config = {{ symO, symX, null, null, null, null }, //1
										  { symO, symX, symO, null, null, null }, //2
										  { symO, symX, symO, null, null, null }, //3
							/*Bottom*/    { symO, symX, symO, symO, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board3Config = {{ symX, symX, symX, symO, null, null }, //1
										  { symX, symX, symO, null, null, null }, //2
										  { symX, symO, symO, null, null, null }, //3
							/*Bottom*/    { symO, symX, symO, symO, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board4Config = {{ symX, symX, null, null, null, null }, //1
										  { null, null, null, null, null, null }, //2
										  { null, null, null, null, null, null }, //3
							/*Bottom*/    { symX, symO, symO, symO, null, null }, //4  /*Top*/
										  { symX, symX, symX, symX, null, null }, //5
										  { symO, symO, null, null, null, null }, //6
										  { symO, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board5Config = {{ symX, symX, null, null, null, null }, //1
										  { symX, symX, symX, null, null, null }, //2
										  { symO, symX, symO, symX, null, null }, //3
							/*Bottom*/    { symX, symO, null, null, null, null }, //4  /*Top*/
										  { symX, symO, symX, symO, symO, symX }, //5
										  { symO, symO, symX, null, null, null }, //6
										  { symO, symX, symO, symO, symO, symX }};//7
		
		ConsolePlayer[][] board6Config = {{ null, null, null, null, null, null }, //1
										  { null, null, null, null, null, null }, //2
										  { null, null, null, null, null, null }, //3
							/*Bottom*/    { null, null, null, null, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7

		//Create the six boards to be copied with the six board configurations:
		Board board1 = new Board(board1Config);
		Board board2 = new Board(board2Config);
		Board board3 = new Board(board3Config);
		Board board4 = new Board(board4Config);
		Board board5 = new Board(board5Config);
		Board board6 = new Board(board6Config);
		
		//Test boards with winners:
		assertEquals(symO, Game.detectWinner(board1, inRow));//Test ID# UI-GdW-1
		assertEquals(symO, Game.detectWinner(board2, inRow));//Test ID# UI-GdW-2
		assertEquals(symO, Game.detectWinner(board3, inRow));//Test ID# UI-GdW-3
		assertEquals(symX, Game.detectWinner(board4, inRow));//Test ID# UI-GdW-4
		
		//Test boards with no winners:
		assertNull(Game.detectWinner(board5, inRow));//Test ID# UI-GdW-5
		assertNull(Game.detectWinner(board6, inRow));//Test ID# UI-GdW-6
	}

}