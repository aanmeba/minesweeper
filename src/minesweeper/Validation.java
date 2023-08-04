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
	
	public int getMaxIndex() {
		return this.maxIndex;
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
		if (isValid != true) Print.printInvalidInput();
	}
	
	public void checkDuplication(Cell[][] board, int x, int y, boolean isFlag) {
		
		// 1. coords -> F / 2. flag -> T
		if (!isFlag) {
			
			// If the cell is previously selected -> getReveal() == true
			isValid = !board[y][x].getReveal();
		} else {
			isValid = !board[y][x].getReveal() || board[y][x].getFlag();
		}
		
		if (!isValid) {
			Print.printDuplication();
		}
		
	}
		
	
	
	public boolean removeFlag(Cell[][] board, Cell[][] mines, int x, int y, boolean isFlag) {
		if (board[y][x].getFlag() && isFlag) {
			board[y][x].setFlag(false);
			
			Print.printRevealFlag();
			
			if (mines[y][x].getMine()) {
				this.isMine = true;				
			}
			isValid = true;
		
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean hasWon (Cell[][] board, Cell[][] mines, int size) {
		String results = "";
		
		// if the number of flags == the number of mines
		// loop the board and check they are placed in the same positions
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				if (board[i][j].getFlag()) {
					if (mines[i][j].getMine()) {
						results += "t";
					} else {
						results = "";
					}
				}		
			}
		}
		return results != "";
	}
	
	
//    public int validateInputs(String inputType, Scanner scanner, Function<Integer, Boolean> validator) {
//        int inputValue = 0;
//        
//        System.out.println(inputType);
//
//        while (true) {
//        	
//        	this.setString(inputType);
//        	
//            try {
//                inputValue = scanner.nextInt();
//                boolean validInput = validator.apply(inputValue);
//                this.printValidationMessage();
//
//                if (validInput) break;
//                
//            } catch (InputMismatchException e) {
//                System.out.println("-- Invalid input. Please enter a valid integer. --");
//                scanner.nextLine(); // consume the invalid input
//            }
//        }
//        return inputValue;
//    }
    
//    public int validateInputs(String inputType, Scanner scanner, Function<Integer, Boolean> validator, String str) {
//        int inputValue = 0;
//        
//        System.out.println(inputType);
//
//        while (true) {
//        	
//        	System.out.printf("=> Please enter your %s coordinate\n", str);
//        	
//            try {
//                inputValue = scanner.nextInt();
//                boolean validInput = validator.apply(inputValue);
//                this.printValidationMessage();
//
//                if (validInput) break;
//                
//            } catch (InputMismatchException e) {
//                System.out.println("-- Invalid input. Please enter a valid integer. --");
//                scanner.nextLine(); // consume the invalid input
//            }
//        }
//        return inputValue;
//    }
    
    
//    public void setString(String type) {
//    	
//    	switch(type) {
//    	case "option":
//    		System.out.println("=> Options:\n1. Select coordinates\n2. Place a flag\n");
//    		break;
//    	case "boardSize":
//    		System.out.println("boardSize -----");
//    		System.out.printf("=> Enter the board size you want between %d and %d\n", 
//    				this.minBoardSize, this.maxBoardSize);
//    		break;
//    	default:
//    		break;
//    	}
//    }
		
}
