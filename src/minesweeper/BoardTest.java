package minesweeper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

	Board underTest;
	int boardSize = 5;
	Cell[][] minesCoords;
	Cell[][] gameBoard;
	char[][] calculatedBoard;
	int mineX = 0;
	int mineY = 0;
	int flagX = 3;
	int flagY = 3;
	int revealX = 4;
	int revealY = 4;
	
	@BeforeEach
	void setUpBoard() {
		minesCoords = new Cell[boardSize][boardSize];
		minesCoords[mineY][mineX] = new Cell();
		minesCoords[mineY][mineX].setMine(true);
		
		gameBoard = new Cell[boardSize][boardSize];
		gameBoard[flagY][flagX] = new Cell();
		gameBoard[revealY][revealX] = new Cell();
		
		calculatedBoard = new char[boardSize][boardSize];
		calculatedBoard[mineY][mineX] = ' ';
		calculatedBoard[flagY][flagX] = ' ';
		calculatedBoard[revealY][revealX] = ' ';
				
		underTest = new Board(minesCoords, gameBoard);			
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
		assertEquals(true, received);
	}
	
	@Test
	public void placeWhat_IfIsFlagIsTrue_SetFlagAndSetReveal() {
		int x=3;
		int y=3;
		int num = 0;
		boolean isFlag = true;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean flagged = gameBoard[y][x].getFlag();
		assertEquals(true, flagged);
		
		boolean revealed = gameBoard[y][x].getReveal();
		assertEquals(true, revealed);
	}
	
	@Test
	public void placeWhat_IfNumIsZero_SetRevealTrue() {
		int x=4;
		int y=4;
		int num = 0;
		boolean isFlag = false;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean revealed = gameBoard[y][x].getReveal();
		assertEquals(true, revealed);
	}
	
	@Test
	public void placeWhat_IfNumIsNotZero_SetRevealTrueAndSetNeighbour() {
		int x=4;
		int y=4;
		int num = 2;
		boolean isFlag = false;
		
		underTest.placeWhat(x, y, num, isFlag);
		boolean revealed = gameBoard[y][x].getReveal();
		assertEquals(true, revealed);
		
		int foundNeighbour = gameBoard[y][x].getNeighbour();
		assertEquals(num, foundNeighbour);
	}
	
	
//	@Test
//	public void calculateCellsInBoard_AssignCorrectMarks() {
//		
//		// minesCoords
//		underTest.calculateCellsInBoard(minesCoords, "mine");
//		char foundMine = calculatedBoard[mineY][mineX];
//		assertEquals('*', foundMine);
//		
//	}
	
	
}
