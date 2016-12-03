package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connect.four.player.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Class;
import java.lang.reflect.Method;

import connect.four.board.*;

public class TestConsolePlayer {
	
//--START code modified from user dfa on stackoverflow.com----------------------------//
//--(http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println)----//
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
//--END code modified from user dfa on stackoverflow.com------------------------------//
	
	@Test
	public void testDumpBoard() {
		
		//Instantiate the console players, and create a method variable for dumpBoard():
		ConsolePlayer adam = new ConsolePlayer("adam the @ player");
		ConsolePlayer xian = new ConsolePlayer("xian the X player");
		Method dumpBoard = null;
		
		//Setup the strings representing the expected output:
		String expectedOutput1 = "@ is you, X is the other player, and O is empty." + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "1234567" + System.lineSeparator();
		
		String expectedOutput2 = "@ is you, X is the other player, and O is empty." + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "OOOOOOO" + System.lineSeparator() +
								 "O@OOOOO" + System.lineSeparator() +
								 "OXOOO@O" + System.lineSeparator() +
								 "@X@OOXO" + System.lineSeparator() +
								 "1234567" + System.lineSeparator();
		
		String expectedOutput3 = "@ is you, X is the other player, and O is empty." + System.lineSeparator() +
								 "OOOXOOO" + System.lineSeparator() +
								 "OOO@OOO" + System.lineSeparator() +
								 "OOOXOOO" + System.lineSeparator() +
								 "OOO@OOO" + System.lineSeparator() +
								 "OOOXOOO" + System.lineSeparator() +
								 "OOO@OOO" + System.lineSeparator() +
								 "1234567" + System.lineSeparator();
		
		//Setup the three board configurations:
		ConsolePlayer[][] board1Config = {{ null, null, null, null, null, null }, //1
										  { null, null, null, null, null, null }, //2
										  { null, null, null, null, null, null }, //3
							/*Bottom*/    { null, null, null, null, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board2Config = {{ adam, null, null, null, null, null }, //1
										  { xian, xian, adam, null, null, null }, //2
										  { adam, null, null, null, null, null }, //3
							/*Bottom*/    { null, null, null, null, null, null }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { xian, adam, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		ConsolePlayer[][] board3Config = {{ null, null, null, null, null, null }, //1
										  { null, null, null, null, null, null }, //2
										  { null, null, null, null, null, null }, //3
							/*Bottom*/    { adam, xian, adam, xian, adam, xian }, //4  /*Top*/
										  { null, null, null, null, null, null }, //5
										  { null, null, null, null, null, null }, //6
										  { null, null, null, null, null, null }};//7
		
		//Create the boards with the three board configurations
		Board board1 = new Board(board1Config);
		Board board2 = new Board(board2Config);
		Board board3 = new Board(board3Config);
		
		//Get the private method dumpBoard out of class ConsolePlayer so we can test it:
		try{
			dumpBoard = ConsolePlayer.class.getDeclaredMethod("dumpBoard", new Class[] { ReadableBoard.class });
		}
		catch(NoSuchMethodException e){
			fail("No such method exception thrown.");
		}
		catch(Exception e){
			fail("Something went wrong; I don't know what.");
		}
		
		//dumpBoard is private, so we must make it accessible:
		dumpBoard.setAccessible(true);
		
		//Perform the tests:
		try{
			//Test 1 
			dumpBoard.invoke(adam, new Object[] { board1 });
			assertEquals(expectedOutput1, outContent.toString());
			outContent.reset();
			
			//Test 2 
			dumpBoard.invoke(adam, new Object[] { board2 });
			assertEquals(expectedOutput2, outContent.toString());
			outContent.reset();
			
			//Test 3 
			dumpBoard.invoke(adam, new Object[] { board3 });
			assertEquals(expectedOutput3, outContent.toString());
			outContent.reset();
		}
		catch(Exception e){
			fail("Something went wrong; I didn't bother to find out what.");
		}
	}

}