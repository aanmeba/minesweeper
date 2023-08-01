package minesweeper;

public class Validation extends Minesweeper {

	private boolean isValid;
	
	
	public Validation(int boardSize) {
		this.maxIndex = boardSize - 1;
	}
	
	public boolean getIsValid() {
		return this.isValid;
	}
	
	public boolean getIsMine() {
		return this.isMine;
	}

	public boolean validateInputRange(int inputNum) {
		isValid = !(inputNum > this.maxIndex || inputNum < 0);
		return isValid;
	}
	
	public boolean validateSizeRange(int inputNum) {
		isValid = (inputNum >= this.minBoardSize && inputNum <= this.maxBoardSize);
		return isValid;
	}
	
	public boolean validateOption(int inputNum) {
		isValid = (inputNum == 1 || inputNum == 2);
		return isValid;
	}
	
	public void printValidationMessage() {
		if (isValid != true) {
			System.out.println("-- Invalid input. Please enter a valid integer. --");
		}
	}
	
	public void checkDuplication(int[][] array, int x, int y) {
		isValid = !(array[y][x] != 0 && array[y][x] != 88);
		if (!isValid) {
			System.out.println("-- You've already used that. Please enter a different integer. --");
		}
		
	}
	
	public boolean removeFlag(int[][] board, int[][] mines, int x, int y, boolean isFlag) {
		if (board[y][x] == 88 && isFlag) {
			
			System.out.println("-- It was flagged coordinates. You can now reveal it. --");
			
			if (mines[y][x] == 100) {
				this.isMine = true;				
			} else {
				board[y][x] = 0;
			}
			isValid = true;
		
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean checkYesOrNo(String input) {
		return (input.toLowerCase().equals("y") || input.toLowerCase().equals("n")); 
	}
	
	public boolean wantHack(String input) {
		if (input.toLowerCase().equals("y")) return true;
		return false;		
	}
		
}
