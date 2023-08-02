package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

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
	
	public boolean finishGame() {
		this.isGameRunning = false;
		return this.isGameRunning;
	}
	
	public void gameOver(boolean maybeMine, boolean isFlag) {
		gameBoard.printBoard(maybeMine, isFlag);
		this.finishGame();
		Print.printTitle(" Game Over ");
	}
	
	public int getCoordinateInput(char xy, Scanner scanner) {
		
//		return validation.validateInputs("coordinate", scanner, validation::validateInputRange, xy);
		int coord = 0;
		
		while (true) {
			System.out.printf("=> Please enter your %s coordinate\n", xy);
			
			try {
				coord = scanner.nextInt();
				boolean validInput = validation.validateInputRange(coord);
				validation.printValidationMessage();
				
				if (validInput) break;
			} catch(InputMismatchException e) {
				 // catch other input type
				System.out.println("-- Invalid input. Please enter a valid integer. --");
	            scanner.nextLine(); // consume the invalid input
			}
		
		}
		return coord;
	}
	
	public int getFlagOrNum(Scanner scanner) {
//		return validation.validateInputs("option", scanner, validation::validateOption);
		int option;
		
		while (true) {
			System.out.println("=> Options:\n1. Select coordinates\n2. Place a flag\n");

			try {
				option = scanner.nextInt();
				boolean validInput = validation.validateOption(option);
				validation.printValidationMessage();
				Print.lineBreaker(2);
			
				if (validInput) break;
			} catch(InputMismatchException e) {
				 // catch other input type
				System.out.println("-- Invalid input. Please enter a valid integer. --");
	            scanner.nextLine(); // consume the invalid input
			}
				
		}
		return option;
	}
	
	public int getBoardSize(Scanner scanner) {
		
//		return validation.validateInputs("boardSize", scanner, validation::validateSizeRange);
		
		int size;
		
		while (true) {
			System.out.printf("=> Enter the board size you want between %d and %d\n", 
					this.minBoardSize, this.maxBoardSize);
			try {
				size = scanner.nextInt();
				validation = new Validation(size);
				
				boolean validInput = validation.validateSizeRange(size);
				validation.printValidationMessage();
				
				if (validInput) break;
			 } catch(InputMismatchException e) {
				 // catch other input type
	            System.out.println("-- Invalid input. Please enter a valid integer. --");
	            scanner.nextLine(); // consume the invalid input
			 }
		}
		return size;
	}
	
	public boolean revealFlag (boolean isFlag, int coordX, int coordY) {
		return validation.removeFlag(
				gameBoard.getGameBoard(), 
				gameBoard.getMinesCoords(), 
				coordX, coordY, isFlag);		
	}
		
	
	public void runGame() {
		Print.printTitle("Minesweeper");
		Print.getInstruction(this.minBoardSize, this.maxBoardSize);
		Scanner scanner = new Scanner(System.in);
		
		int boardSize = this.getBoardSize(scanner);
		gameBoard = new Board(boardSize);
		Print.lineBreaker(2);
		
		
		Print.printTitle("Let's Start!");
		Print.printIndicator("Your board");
		// print an empty board
		gameBoard.printBoard(false, false);			
		
		if (this.isHacked) {
			Print.printIndicator("Your hacked board");
			gameBoard.printBoard(true, false);
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
					// set isValid value in the Validation class
					validation.checkDuplication(gameBoard.getGameBoard(), coordX, coordY);
					
				}
				
				
				if (isFlag) {
					flagToNum = this.revealFlag(isFlag, coordX, coordY);
					if (flagToNum) gameBoard.setFlagsCount();
				}
				
				
				if (validation.getIsValid()) {
					isMine = gameBoard.isMine(coordX, coordY, isFlag);
					
					if (isMine && !isFlag) {
						this.gameOver(isMine, isFlag);
//						gameBoard.printBoard(isMine, isFlag);
//						this.finishGame();
//						printTitle(" Game Over ");
					} else {
						Print.lineBreaker(2);
						int num = gameBoard.findNeighbour(coordX, coordY);
						
						// flagToNum is true -> want to remove the flag -> return false to render number
						// t && !t -> f
						// t && !f -> t
						// f && !t -> f
						// f && !f -> f
						boolean toggleFlag = (isFlag && !flagToNum);
						
						// if isMine is true -> return true (no matter value of validation.isMine)
						// if isMine is false && validation.isMine is true -> return true
						// if isMine is false && validation.isMine is false -> return false
						boolean isStillMine = !isMine ? validation.getIsMine() : isMine;
						
						if (isStillMine) {
							this.gameOver(isStillMine, isFlag);
//							gameBoard.printBoard(isStillMine, isFlag);
//							this.finishGame();
//							printTitle(" Game Over ");
							break;
						}
						
						gameBoard.placeWhat(coordX, coordY, num, toggleFlag);
						Print.printIndicator("Your board");
						gameBoard.printBoard(isMine, toggleFlag);
						
						if (isHacked) {
							Print.printIndicator("Your hacked board");
							gameBoard.printBoard(true, false);
						}
						
						// to win the game, player has to place flags
						System.out.printf("mines - %d, flags = %d", gameBoard.getMinesCount(), gameBoard.getFlagsCount());
						if (gameBoard.getMinesCount() == gameBoard.getFlagsCount()) {
							playerWon = validation.hasWon(gameBoard.getGameBoard(), gameBoard.getMinesCoords(), this.boardSize);
							System.out.printf("Yes! mines == flags, %b", playerWon);
						}
						
						if (playerWon) {
							Print.printTitle("YOU WON!!");
							this.finishGame();
							break;
						}
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("-- Invalid input. Please enter a valid integer. --");
				scanner.nextLine(); // consume the invalid input
			}			
		}
		scanner.close();
	}

}
