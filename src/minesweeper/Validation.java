package minesweeper;

public class Validation {

	private final int minBoardSize = 5;
	private boolean isValid;
	private int maxIndex;
	private int maxBoardSize = 15;
	
	public Validation(int boardSize) {
		this.maxIndex = boardSize - 1;
	}
	
	public boolean getIsValid() {
		return this.isValid;
	}

	public boolean validateInputRange(int inputNum) {
		if (inputNum > this.maxIndex || inputNum < 0) {
			isValid = false;
		} else {			
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateSizeRange(int inputNum) {
		if (inputNum >= this.minBoardSize && inputNum <= this.maxBoardSize) {
			isValid = true;
		} else {			
			isValid = false;
		}
		return isValid;
	}
	
	public boolean validateOption(int inputNum) {
		if (inputNum == 1 || inputNum == 2) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
	
	public void printValidationMessage() {
		if (isValid != true) {
			System.out.println("Invalid input. Please enter a valid integer.");
		}
	}
	
	public void checkDuplication(int[][] array, int x, int y) {
		if (array[y][x] != 0) {
			System.out.println("You've already used that. Please enter a different integer.");
			isValid = false;
		} else {
			isValid = true;
		}
		
	}
	
	
}
