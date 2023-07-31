package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Run extends Minesweeper {
	
//	public boolean isMine;
	private boolean isGameRunning;
	private Board gameBoard;
	private Validation validation;
	private boolean isHacked = true;
	private boolean playerWon = false;
	
	Run() {
		this.isGameRunning = true;
	}
	
	public boolean getIsGameRunning() {
		return this.isGameRunning;
	}
	
	public boolean finishGame() {
		this.isGameRunning = false;
		return this.isGameRunning;
	}
	
	public void printTitle(String message) {
		System.out.println("=======================================");
		System.out.printf("************* %s *************\n", message);
		System.out.println("=======================================");
	}
	
	public void printIndicator(String message) {
		System.out.printf("# %s :\n", message);
	}
	
	public int getCoordinateInput(char xy, Scanner scanner) {
		int coord = 0;
		
		while (true) {
			System.out.printf("Please enter your %s coordinate\n", xy);
			coord = scanner.nextInt();
			boolean validInput = validation.validateInputRange(coord);
			validation.printValidationMessage();
			
			if (validInput) break;
		}
		return coord;
	}
	
	public int getFlagOrNum(Scanner scanner) {
		int option;
		
		while (true) {
			System.out.println("Options:\n1. Select coordinates\n2. Place a flag\n");

			option = scanner.nextInt();
			boolean validInput = validation.validateOption(option);
			validation.printValidationMessage();
			this.lineBreaker(2);
			
			if (validInput) break;
		}
		return option;
	}
	
	public int getBoardSize(Scanner scanner) {
		int boardSize;
		
		while (true) {
			System.out.printf("Enter the board size you want between %d and %d\n", 
					this.minBoardSize, this.maxBoardSize);
			boardSize = scanner.nextInt();
			validation = new Validation(boardSize);
			
			boolean validInput = validation.validateSizeRange(boardSize);
			validation.printValidationMessage();
			
			if (validInput) break;
		}
		return boardSize;
	}
	
	public boolean runGameWithHack(Scanner scanner) {
		boolean wantHack = false;
		
		while (true) {
			
			System.out.println("Play with hack version?\n"
					+ "Y/y for Yes, N/n for No");
			String answer = scanner.nextLine();
			
			boolean validInput = validation.checkYesOrNo(answer);
			
			if (validInput) wantHack = validation.wantHack(answer);
			if (validInput) break;
		}		
		return wantHack;
	}
	
	
	public void runGame() {
		this.printTitle("Minesweeper");
		this.getInstruction();
		Scanner scanner = new Scanner(System.in);
		
		int boardSize = this.getBoardSize(scanner);
		gameBoard = new Board(boardSize);
		this.lineBreaker(2);
		
		isHacked = runGameWithHack(scanner);
		
		this.printTitle("Let's Start!");
		this.printIndicator("Your board");
		// print an empty board
		gameBoard.printBoard(false, false);			
		
		while (this.isGameRunning) {
			if (isHacked) {
				this.printIndicator("Your hacked board");
				gameBoard.printBoard(true, false);
			}
			
			try {
				this.lineBreaker(2);
				int option = getFlagOrNum(scanner);
				
				boolean isFlag = (option == 1) ? false : true;
				boolean flagToNum = false;
				
				int coordX = this.getCoordinateInput('x', scanner);
				int coordY = this.getCoordinateInput('y', scanner);
				System.out.printf("coordX %d, coordY %d\n", coordX, coordY);
								
				if (validation.getIsValid()) {
					// set isValid value
					validation.checkDuplication(gameBoard.getGameBoard(), coordX, coordY);
					
				}
				
				if (isFlag) {
					flagToNum = validation.removeFlag(
							gameBoard.getGameBoard(), 
							gameBoard.getMinesCoords(), 
							coordX, coordY, isFlag);
					System.out.printf("isFlag is turn into %b  -> %b! now \n", isFlag, flagToNum);
				}
				
				
				if (validation.getIsValid()) {
					isMine = gameBoard.isMine(coordX, coordY, isFlag);
					
					if (isMine && !isFlag) {
						gameBoard.printBoard(isMine, isFlag);
						this.finishGame();
						printTitle(" Game Over ");
					} else {
						this.lineBreaker(2);
						int num = gameBoard.findNeighbour(coordX, coordY);
						
						
						// if isFlag is true && flagToNum is true -> return false
						// if isFlag is true && flagToNum is false -> return true	
						// if isFlag is false && flagToNum is false -> return false
						// if isFlag is false && flagToNum is true -> return false
						
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
							gameBoard.printBoard(isStillMine, isFlag);
							this.finishGame();
							printTitle(" Game Over ");
							break;
						}
						
						gameBoard.placeWhat(coordX, coordY, num, toggleFlag);
						this.printIndicator("Your board");
						gameBoard.printBoard(isMine, toggleFlag);
						if (gameBoard.getMinesCount() == gameBoard.getFlagsCount()) {
							System.out.println("mines == flags!! ");
							playerWon = gameBoard.hasWon();
						}
						
						if (playerWon) {
							this.printTitle("YOU WON!!");
							this.finishGame();
							break;
						}
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid integer.");
				scanner.nextLine(); // consume the invalid input
			}			
		}
		scanner.close();
	}
		
	public void lineBreaker(int num) {
		for (int i=0; i<num; i++) {
			System.out.println();			
		}
	}
	
	public void getInstruction() {
		this.lineBreaker(1);
		System.out.println("------------------- Game Instructions -------------------");
		System.out.println("How to Set Up Your Game:");
		System.out.printf("1. Choose your board size between %d and %d\n", this.minBoardSize, this.maxBoardSize);
		System.out.println(" - The number of mines will vary based on your board size");
		System.out.println(" - The number of hidden mines will be revealed once you enter the board size");
		System.out.println("2. Select the play mode with or without the \"hacked\" version.");
		System.out.println(" - The hacked version shows you the locations of all the mines!");
		this.lineBreaker(1);
		System.out.println("How to Play:");
		System.out.println("1. Enter \"1\" to select coordinates or \"2\" to place a flag");
		System.out.println("2. Input the x and y coordinates separately when prompted");
		System.out.println("3. If you choose to place a flag (option \"2\"), the cell will be marked as \"@\" and protected");
		System.out.println("4. To reveal a flagged cell, enter the same coordinates again");
		System.out.println("5. If you select a cell with a hidden mine, the game will be over");
		System.out.println("6. Continue playing until you find all the hidden mines by placing flags");
		System.out.println("---------------------------------------------------------");
		this.lineBreaker(2);

	}

}
