package connect.four.board;

import connect.four.player.Player;

public class TestableBoard extends Board {

	public TestableBoard(int width, int height) {
		super(width, height);
	}

	public void setPlayerPiece(int row, int col, Player p) {
		m_contents[row][col] = p;
	}
}