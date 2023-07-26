package minesweeper;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Run game = new Run();
		game.runGame();
//		Board gi = new Board(); 
		
		
//		Scanner scanner = new Scanner(System.in);
//		while (game.getIsGameRunning()) {
//						
//			System.out.println("Please enter your x coord");
//			int coordX = scanner.nextInt();
//			
//			
//			System.out.println("Please enter your y coord");
//			int coordY = scanner.nextInt();
//			
//			
//			boolean isMine = gi.isMine(coordX, coordY);
//			if (isMine) {
//				// terminate game
//				System.out.println("============= Game Over =============");
//				gi.printBoard(isMine);
//				game.finishGame();
//			} else {
//				int num = gi.findNeighbour(coordX, coordY);
//				gi.placeNum(coordX, coordY, num);
//				gi.printBoard(isMine);
//				
//			}
//			// check the coord 0
//			// find the neighbour
//			// input validation + duplicate input
//			
//		}
//		scanner.close();
	}

}
