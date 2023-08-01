package minesweeper;

public class Validation extends Minesweeper {

	private boolean isValid;
	
//	public Validation() {}
	
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
