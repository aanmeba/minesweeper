package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidationTest {
	
	Validation underTest;
	int testSize = 10;
	
	@BeforeEach
	void setUp() {
		underTest = new Validation(testSize);
	}
	
	@Test
	public void Validation_InitializeCorrectly() {
		
		assertEquals((testSize - 1), underTest.getMaxIndex());
	}
	
	@Test
	public void validateInputRange_SetIsValidCorrectly() {
		boolean greaterThanValid = underTest.validateInputRange(testSize+1);
		assertEquals(greaterThanValid, underTest.getIsValid());
		
		boolean isValid = underTest.validateInputRange(testSize-1);
		assertEquals(isValid, underTest.getIsValid());
		
		boolean negative = underTest.validateInputRange(-1);
		assertEquals(negative, underTest.getIsValid());
	}
	
	@Test
	public void validateSizeRange_SetIsValidCorrectly() {
		boolean greaterThanValid = underTest.validateSizeRange(20);
		assertEquals(greaterThanValid, underTest.getIsValid());
		assertEquals(false, greaterThanValid);
		
		boolean lessThanValid = underTest.validateSizeRange(3);
		assertEquals(lessThanValid, underTest.getIsValid());
		assertEquals(false, lessThanValid);
		
		boolean validRange = underTest.validateSizeRange(testSize);
		assertEquals(validRange, underTest.getIsValid());
		assertEquals(true, validRange);
	}
	
	@Test
	public void validateOption_SetIsValidCorrectly() {
		boolean invalidOption = underTest.validateOption(3);
		assertEquals(invalidOption, underTest.getIsValid());
		assertEquals(false, invalidOption);
		
		boolean validOption = underTest.validateOption(1);
		assertEquals(validOption, underTest.getIsValid());
		assertEquals(true, validOption);		
	}
	
	@Test
	public void checkDuplication_SetIsValidCorrectly() {
		/** 
		 * 0. options is selected - reveal a cell OR place a flag
		 * 
		 * ** cases return true
		 * 1. no duplicated coords
		 * 2. duplicated coords to place a flag for a revealed cell
		 * 3. duplicated coords to remove a flag for a flagged cell
		 * 
		 * ** cases return false
		 * 4. duplicated coords for a revealed cell  
		 * */
		
		Board testBoard = new Board(testSize);
		boolean isFlaggedTrue = true;
		boolean isFlaggedFalse = false;
		
		// 1		
		underTest.checkDuplication(testBoard.getGameBoard(), 3, 3, isFlaggedFalse);
		assertEquals(true, underTest.getIsValid());
		
		// 2
		testBoard.getGameBoard()[3][3].setFlag(true);
		underTest.checkDuplication(testBoard.getGameBoard(), 3, 3, isFlaggedTrue);
		assertEquals(true, underTest.getIsValid());
		
		// 3
		testBoard.getGameBoard()[5][5].setFlag(true);
		underTest.checkDuplication(testBoard.getGameBoard(), 5, 5, isFlaggedTrue);
		assertEquals(true, underTest.getIsValid());
		
		// 4 
		testBoard.getGameBoard()[4][4].setNeighbour(2);
		
		underTest.checkDuplication(testBoard.getGameBoard(), 4, 4, isFlaggedFalse);		
		assertEquals(false, underTest.getIsValid());
	}
	
	@Test
	public void removeFlag_SetValuesCorrectly_And_ReturnBoolean() {
		Board testBoard = new Board(testSize);
		
		// 1. currently flag -> remove flag -> cell with number
		// 2. currently flag -> remove flag -> cell with mine
		
		// currently flagged cell
		testBoard.getGameBoard()[3][3].setFlag(true);
		boolean isFlagTrue = true;
		
		// 1
		underTest.removeFlag(testBoard.getGameBoard(), 
				testBoard.getMinesCoords(), 3, 3, isFlagTrue);
		boolean flagToNum = testBoard.getGameBoard()[3][3].getFlag();
		assertEquals(false, flagToNum);
		
		// 2
		testBoard.getMinesCoords()[1][1].setMine(true);
		testBoard.getGameBoard()[1][1].setFlag(true);
		
		underTest.removeFlag(testBoard.getGameBoard(), 
				testBoard.getMinesCoords(), 1, 1, isFlagTrue);
		boolean flagToMine = testBoard.getGameBoard()[1][1].getFlag();
		assertEquals(false, flagToMine);
		assertEquals(true, underTest.getIsMine());
		assertEquals(true, underTest.getIsValid());	
		
	}
	
	@Test
	public void hasWon_ReturnBoolean() {
		Board testBoard = new Board(testSize);
		
		// randomly place flags
		int index = 0;
		while(index < testSize) {
			int x = testBoard.getRandomNum();
			int y = testBoard.getRandomNum();
			
			if (!testBoard.getGameBoard()[y][x].getFlag()) {
				testBoard.getGameBoard()[y][x].setFlag(true);
				index ++;
			} else {
				continue;
			}
		}
			
		boolean gameResult = underTest.hasWon(testBoard.getGameBoard(), 
				testBoard.getMinesCoords(), testSize);
		assertEquals(false, gameResult);
		
		
	}
	
	
	
	
	
	
	
	
	
	

}
