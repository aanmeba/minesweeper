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
	
//	public void setIsValid(boolean bool) {
//		this.isValid = bool;
//	}
//	
//	public void getIsMine(boolean bool) {
//		this.isMine = bool;
//	}	

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
	
	public void checkDuplication(Cell[][] array, int x, int y) {
		isValid = !(array[y][x].getReveal() && array[y][x].getFlag());
		if (!isValid) {
//			array[y][x].setFlag(false); // ???
			System.out.println("-- You've already used that. Please enter a different integer. --");
		}
		
	}
	
	public boolean removeFlag(Cell[][] board, Cell[][] mines, int x, int y, boolean isFlag) {
		if (board[y][x].getFlag() && isFlag) {
			board[y][x].setFlag(false); 
//			this.flagsCount--; // ** HAVE TO HANDLE FLAGSCOUNT!!!
			
			System.out.println("-- It was flagged coordinates. You can now reveal it. --");
			
			if (mines[y][x].getMine()) {
				this.isMine = true;				
			} else {
				board[y][x].setReveal(true);
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
