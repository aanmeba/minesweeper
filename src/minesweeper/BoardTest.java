package minesweeper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTest {

	Board underTest;
	int testSize = 10;

	@BeforeEach
	void setUpBoard() {	
		underTest = new Board(testSize);
	}
	
	@Test
	public void Board_InitializeCorrectly() {
		int size = 5;
		Board board = new Board(size);
		assertEquals(size, board.getBoardSize());
		assertEquals((size-1), board.getMaxIndex());
		assertEquals(size, board.getMinesCoords().length);
		assertEquals(size, board.getGameBoard().length);
		assertEquals(size, board.getCalculatedBoard().length);
		assertEquals(size, board.getMinesCount());
	}
	
	@Test
	public void setDifficulty_SetMinesCountCorrectly() {
		Board board1 = new Board(4);
		assertEquals(3, board1.getMinesCount());
		
		Board board2 = new Board(7);
		assertEquals(5, board2.getMinesCount());
		
		Board board3 = new Board(11);
		assertEquals(10, board3.getMinesCount());
		
		Board board4 = new Board(12);
		assertEquals(20, board4.getMinesCount());
		
		Board board5 = new Board(14);
		assertEquals(25, board5.getMinesCount());
		
		Board board6 = new Board(15);
		assertEquals(30, board6.getMinesCount());
	}
	
	@Test
	public void generateMinesCoords_GenerateCorrectNumberOfMines() {
		Board board = new Board(10);
				
		board.generateMinesCoords();
		assertEquals(10, board.getMinesCount());
	}
	
	@Test
	public void isMine_IfIsFlagIsTrue_ReturnsFalse() {
		
		int x=0;
		int y=0;
		boolean isFlag = true;
		
		boolean received = underTest.isMine(x, y, isFlag);
		assertFalse(received);
	}
	
	@Test
	public void isMine_ReturnsBoolean() {
		
		int x=0;
		int y=0;
		boolean isFlag = false;
		
		boolean received = underTest.isMine(x, y, isFlag);
		assertEquals(underTest.getMinesCoords()[y][x].getMine(), received);
		
	}
	
	@Test
	public void placeWhat_IfIsFlagIsTrue_SetFlagAndSetReveal() {
		
		int x=3;
		int y=3;
		int num = 0;
		boolean isFlag = true;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean flagged = underTest.getGameBoard()[y][x].getFlag();
		assertEquals(true, flagged);
		
		boolean revealed = underTest.getGameBoard()[y][x].getReveal();
		assertEquals(true, revealed);
	}
	
	@Test
	public void placeWhat_IfNumIsZero_SetRevealTrue() {
		
		int x=4;
		int y=4;
		int num = 0;
		boolean isFlag = false;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean revealed = underTest.getGameBoard()[y][x].getReveal();
		System.out.println("************ +++++++++");
		System.out.println(revealed);
		assertEquals(true, revealed);
	}
	
	@Test
	public void placeWhat_IfNumIsNotZero_SetRevealTrueAndSetNeighbour() {
		
		int x=4;
		int y=4;
		int num = 2;
		boolean isFlag = false;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean revealed = underTest.getGameBoard()[y][x].getReveal();
		assertEquals(true, revealed);
		
		int foundNeighbour = underTest.getGameBoard()[y][x].getNeighbour();
		assertEquals(num, foundNeighbour);
	}
	
	@Test
	public void whichBoard_ReturnCorrectBoard() {
		
		Cell[][] boardWithMine = underTest.whichBoard(true);
		assertEquals(underTest.getMinesCoords(), boardWithMine);
		
		Cell[][] boardWithMNoine = underTest.whichBoard(false);
		assertEquals(underTest.getGameBoard(), boardWithMNoine);
	}
	
	@Test
	public void whichType_ReturnCorrectString() {
		
		String mine = underTest.whichType(true, false);
		assertEquals("mine", mine);
		
		String flag = underTest.whichType(false, true);
		assertEquals(1, underTest.getFlagsCount());
		assertEquals("flag", flag);
		
		String reveal = underTest.whichType(false, false);
		assertEquals("reveal", reveal);
	}
	
	@Test
	public void calculateCellsInBoard_AssignAsterisk() {
		
		// minesCoords
		underTest.calculateCellsInBoard(underTest.getMinesCoords(), "mine");
		
		int minesCount = 0;
		for (char[] row: underTest.getCalculatedBoard()) {
			for (char cell: row) {
				if (cell == '*') minesCount++;
			}
		}
		assertEquals(testSize, minesCount);		
	}
	
	@Test
	public void calculatedCellsInBoard_AssignCorrectMarks() {
		
		// flag
		// do something like placeWhat();
		underTest.getGameBoard()[5][5].setFlag(true);
		underTest.getGameBoard()[5][5].setReveal(true);
		underTest.calculateCellsInBoard(underTest.getGameBoard(), "flag");
		char flag = underTest.getCalculatedBoard()[5][5];
		assertEquals('@', flag);
		
		// reveal - no mine around
		underTest.getGameBoard()[1][1].setReveal(true);
		underTest.calculateCellsInBoard(underTest.getGameBoard(), "reveal");
		char zero = underTest.getCalculatedBoard()[1][1];
		assertEquals('0', zero);
		
		// reveal - mines around
		int minesAround = 2;
		underTest.getGameBoard()[3][3].setReveal(true);
		underTest.getGameBoard()[3][3].setNeighbour(minesAround);
		underTest.calculateCellsInBoard(underTest.getGameBoard(), "reveal");
		char number = underTest.getCalculatedBoard()[3][3];
		assertEquals((minesAround + '0'), number);
	}		
	
	@Test void outOfBounds_IfReachesBound_ReturnTrue() {
		
		boolean outOfX = underTest.outOfBounds(10, 1);
		assertEquals(true, outOfX);
		
		boolean outOfY = underTest.outOfBounds(4, 10);
		assertEquals(true, outOfY);
	}
	
	@Test
	void outOfBounds_IfDoesntReachBound_ReturnFalse() {
		
		boolean received = underTest.outOfBounds(4, 0);
		assertEquals(false, received);		
	}
	
	@Test
	public void findNeighbour_ReturnCorrectResult() {
		
		int x=0;
		int y=1;
		
		int received = underTest.findNeighbour(x, y);
		assertTrue(received >= 0 && received < testSize);	
	}	
	
}
