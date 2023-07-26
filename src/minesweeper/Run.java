package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Run {
	
	private boolean isGameRunning;
	public boolean isMine;
	private Board gameBoard;
	
	Run() {
		this.isGameRunning = true;
		gameBoard = new Board(); 
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
			System.out.printf("Please enter your %s coord\n", xy);
			coord = scanner.nextInt();
			boolean validInput = Validation.validateInputRange(coord);
			Validation.printValidationMessage();
			
			if (validInput) break;
		}
		return coord;
	}
	
	public void runGame() {
		printTitle("Minesweeper");
		Scanner scanner = new Scanner(System.in);
		
		while (this.isGameRunning) {
			
			try {

				int coordX = getCoordinateInput('x', scanner);
				int coordY = getCoordinateInput('y', scanner);
								
				if (Validation.isValid) {
					Validation.checkDuplication(gameBoard.getGameBoard(), coordX, coordY);
				}
				
				
				if (Validation.isValid) {
					isMine = gameBoard.isMine(coordX, coordY);
					
					if (isMine) {
						gameBoard.printBoard(isMine);
						this.finishGame();
						printTitle(" Game Over ");
					} else {
						int num = gameBoard.findNeighbour(coordX, coordY);
						gameBoard.placeNum(coordX, coordY, num);
						gameBoard.printBoard(isMine);	
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid integer.");
				scanner.nextLine(); // consume the invalid input
			}			
		}
		scanner.close();
	}

}
