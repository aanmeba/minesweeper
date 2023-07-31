package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Run {
	
	private boolean isGameRunning;
	public boolean isMine;
	private Board gameBoard;
	private Validation validation;
	private boolean isHacked = true;
	private boolean playerWon = false;
	
	Run() {
		this.isGameRunning = true;
//		gameBoard = new Board(); 
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
			System.out.println("Want to select coordinates -- 1");
			System.out.println("Want to select a flag      -- 2");
			option = scanner.nextInt();
			boolean validInput = validation.validateOption(option);
			validation.printValidationMessage();
			this.lineBreaker();
			
			if (validInput) break;
		}
		return option;
	}
	
	public int getBoardSize(Scanner scanner) {
		int boardSize;
		
		while (true) {
			System.out.println("Enter the board size you want between 10 and 15");
			boardSize = scanner.nextInt();
			validation = new Validation(boardSize);
			
			boolean validInput = validation.validateSizeRange(boardSize);
			validation.printValidationMessage();
			
			if (validInput) break;
		}
		return boardSize;
	}
	
	public void runGameWithHack() {
		System.out.println();
	}
	
	
	public void runGame() {
		printTitle("Minesweeper");
		Scanner scanner = new Scanner(System.in);
		
		int boardSize = this.getBoardSize(scanner);
		gameBoard = new Board(boardSize);
		
		gameBoard.printBoard(false, false);
		
		// hacked
//		System.out.println("Do you want a HACK? Y for Yes, N for No");
//		char res = scanner.nextLine();
		
		
		
		System.out.printf("-- You have %d mines --\n", gameBoard.getMinesCount());
		
		while (this.isGameRunning) {
			gameBoard.printBoard(true, isHacked);
			try {
				this.lineBreaker();
				int option = getFlagOrNum(scanner);
				
				boolean isFlag = (option == 1) ? false : true;
				
				int coordX = this.getCoordinateInput('x', scanner);
				int coordY = this.getCoordinateInput('y', scanner);
				System.out.printf("coordX %d, coordY %d\n", coordX, coordY);
								
				if (validation.getIsValid()) {
					validation.checkDuplication(gameBoard.getGameBoard(), coordX, coordY);
				}
				
				
				if (validation.getIsValid()) {
					isMine = gameBoard.isMine(coordX, coordY, isFlag);
					
					if (isMine) {
						gameBoard.printBoard(isMine, isFlag);
						this.finishGame();
						printTitle(" Game Over ");
					} else {
						this.lineBreaker();
						int num = gameBoard.findNeighbour(coordX, coordY);
						
						// reveal around the 0
//						if (num == 0) {
//							gameBoard.revealArea(coordX, coordY);
//							gameBoard.printBoard2();
//							gameBoard.printRevealedBoard();
//						}
						
						gameBoard.placeWhat(coordX, coordY, num, isFlag);
						gameBoard.printBoard(isMine, isFlag);
						if (gameBoard.getMinesCount() == gameBoard.getFlagsCount()) {
							System.out.println("mines == flags!! ");
							playerWon = gameBoard.hasWon();
						}
						
						if (playerWon) {
							this.printTitle("YOU WON!!");
							this.finishGame();
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
	
	public void clearConsole() {
//		System.out.print("\033[H\033[2J");
//		System.out.flush();
	}
	
	public void lineBreaker() {
		System.out.println();
		System.out.println();
	}
	
	public void getInstruction() {
		System.out.println("How to play:");
		System.out.println("");
//		Your task is to find and flag all 10 hidden mines on the board without touching them.
//		The game board consists of a 10x10 grid, and each cell is represented by an (x, y) coordinate.
//		To play, enter the x and y coordinates separately when prompted, following the instructions.
//		Only enter numbers between 0 and 9 for each coordinate. For example, if you want to choose the cell at (3, 5), enter 3 as the x-coordinate and 5 as the y-coordinate.
//		Be cautious! If you accidentally choose a cell with a hidden mine, the game will be over.
//		The game ends when you successfully flag all 10 mines or when you trigger a mine by selecting a cell with one.
	}

}
