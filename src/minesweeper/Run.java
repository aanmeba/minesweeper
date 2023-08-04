package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class Run extends Minesweeper {
	
	private boolean isGameRunning;
	private Board gameBoard;
	private Validation validation;
	private boolean playerWon = false;
	private boolean isHacked;
	
	Run() {
		this.isGameRunning = true;
	}
	
	Run(String arg) {
		this.isGameRunning = true;
		this.isHacked = true;
	}
	
	public boolean getIsGameRunning() {
		return this.isGameRunning;
	}
	
	public boolean getIsHacked() {
		return this.isHacked;
	}
	
	public boolean finishGame() {
		this.isGameRunning = false;
		return this.isGameRunning;
	}
		
	public void gameOver(boolean maybeMine, boolean isFlag) {
		calculateBoard(maybeMine, isFlag);
		Print.printBoardFrame(gameBoard.getCalculatedBoard());
		this.finishGame();
		Print.printTitle(" Game Over ");
	}
	
	public int getCoordinateInput(char xy, Scanner scanner) {
		int coord = 0;
		
		while (true) {
			Print.printPromptOption(xy);
			try {
				coord = scanner.nextInt();
				boolean validInput = validateInputs(coord, validation::validateInputRange);
				if (validInput) break;
				
			} catch(InputMismatchException e) { 
				catchInvalidInput(scanner);
			}		
		}
		return coord;
	}
	
	public int getFlagOrNum(Scanner scanner) {
		int option;
		
		while (true) {
			Print.printPromptOption();
			try {
				option = scanner.nextInt();				
				boolean validInput = validateInputs(option, validation::validateOption);			
				if (validInput) break;				
			} catch(InputMismatchException e) { 
				catchInvalidInput(scanner);
			}				
		}
		return option;
	}
	
	public int getBoardSize(Scanner scanner) {
		int size;
		
		while (true) {
			Print.printPromptOption(this.minBoardSize, this.maxBoardSize);
			try {
				size = scanner.nextInt();
				validation = new Validation(size);				
				boolean validInput = validateInputs(size, validation::validateSizeRange);
				if (validInput) break;				
			} catch(InputMismatchException e) { 
				catchInvalidInput(scanner);
			}
		}
		return size;
	}
	
	public void catchInvalidInput(Scanner scanner) {
		Print.printInvalidInput();
        scanner.nextLine(); // consume the invalid input
	}

	public boolean validateInputs(int value, Function<Integer, Boolean> validator) {
		validation.printValidationMessage();
		Print.lineBreaker(2);
		return validator.apply(value);		
	}
	
	
	public boolean revealFlag (boolean isFlag, int coordX, int coordY) {
		return validation.removeFlag(
				gameBoard.getGameBoard(), 
				gameBoard.getMinesCoords(), 
				coordX, coordY, isFlag);		
	}
	
	public void calculateBoard(boolean isMine, boolean isFlag) {
		Cell[][] board = gameBoard.whichBoard(isMine);
		String type = gameBoard.whichType(isMine, isFlag);
		gameBoard.calculateCellsInBoard(board, type);
	}
	
	public void yourBoardGroup(boolean mine, boolean flag) {
		Print.printIndicator("Your board");
		calculateBoard(mine, flag);
		Print.printBoardFrame(gameBoard.getCalculatedBoard());
	}
	
	public void yourBoardGroup(boolean hacked) {
		Print.printIndicator("Your hacked board");
		calculateBoard(hacked, false);
		Print.printBoardFrame(gameBoard.getCalculatedBoard());
	}
	
	public void runGame() {
		Print.printTitle("Minesweeper");
		Print.getInstruction(this.minBoardSize, this.maxBoardSize);
		Scanner scanner = new Scanner(System.in);
		
		int boardSize = this.getBoardSize(scanner);
		
		gameBoard = new Board(boardSize);
		Print.lineBreaker(2);
		
		
		Print.printTitle("Let's Start!");
		this.yourBoardGroup(false, false); // print an empty board
		
		
		if (this.isHacked) {
			this.yourBoardGroup(this.isHacked, false);
		}
		
		while (this.isGameRunning) {
			
			try {
				Print.lineBreaker(2);
				int option = getFlagOrNum(scanner);
				
				boolean isFlag = (option == 1) ? false : true;
				boolean flagToNum = false;
				
				int coordX = this.getCoordinateInput('x', scanner);
				int coordY = this.getCoordinateInput('y', scanner);
								
				if (validation.getIsValid()) {
					validation.checkDuplication(gameBoard.getGameBoard(), coordX, coordY, isFlag);
				}
				
				if (isFlag) {
					flagToNum = this.revealFlag(isFlag, coordX, coordY);
					if (flagToNum) gameBoard.setFlagsCount();
				}
				
				if (validation.getIsValid()) {
					isMine = gameBoard.isMine(coordX, coordY, isFlag);
					
					if (isMine && !isFlag) {
						this.gameOver(isMine, isFlag);
						break;
					} 
					Print.lineBreaker(2);
					int num = gameBoard.findNeighbour(coordX, coordY);
					
					// flagToNum is true -> want to remove the flag -> return false to render number
					boolean toggleFlag = (isFlag && !flagToNum);
					
					// if isMine is true -> return true (no matter value of validation.isMine)
					// if isMine is false && validation.isMine is true -> return true
					// if isMine is false && validation.isMine is false -> return false
					boolean isStillMine = !isMine ? validation.getIsMine() : isMine;
					
					if (isStillMine) {
						this.gameOver(isStillMine, isFlag);
						break;
					}
					
					gameBoard.placeWhat(coordX, coordY, num, toggleFlag);
					this.yourBoardGroup(isMine, toggleFlag);
					
					if (this.isHacked) {
						this.yourBoardGroup(this.isHacked);
					}
					
					// to win the game, player has to place flags
					if (gameBoard.getMinesCount() == gameBoard.getFlagsCount()) {
						playerWon = validation.hasWon(gameBoard.getGameBoard(), 
								gameBoard.getMinesCoords(), this.boardSize);
					}
					
					if (playerWon) {
						Print.printTitle("YOU WON!!");
						this.finishGame();
						break;
					}
				}
			} catch (InputMismatchException e) {
				catchInvalidInput(scanner);
			}			
		}
		scanner.close();
	}

}
