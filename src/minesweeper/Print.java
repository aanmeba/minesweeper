package minesweeper;

public class Print {
		
	public static void printTitle(String message) {
		System.out.println("=======================================");
		System.out.printf("************* %s *************\n", message);
		System.out.println("=======================================");
	}
	
	public static void printIndicator(String message) {
		System.out.printf("# %s :\n", message);
	}
	
	public static void getInstruction(int min, int max) {
		lineBreaker(1);
		System.out.println("------------------- Game Instructions -------------------");
		System.out.println("How to Set Up Your Game:");
		System.out.printf("1. Choose your board size between %d and %d\n", min, max);
		System.out.println(" - The number of mines will vary based on your board size");
		System.out.println(" - The number of hidden mines will be revealed once you enter the board size");
		System.out.println("2. Select the play mode with or without the \"hacked\" version.");
		System.out.println(" - The hacked version shows you the locations of all the mines!");
		lineBreaker(1);
		System.out.println("How to Play:");
		System.out.println("1. Enter \"1\" to select coordinates or \"2\" to place a flag");
		System.out.println("2. Input the x and y coordinates separately when prompted");
		System.out.println("3. If you choose to place a flag (option \"2\"), the cell will be marked as \"@\" and protected");
		System.out.println("4. To reveal a flagged cell, enter the same coordinates again");
		System.out.println("5. If you select a cell with a hidden mine, the game will be over");
		System.out.println("6. Continue playing until you find all the hidden mines by placing flags");
		System.out.println("---------------------------------------------------------");
		lineBreaker(2);

	}
	
	public static void lineBreaker(int num) {
		for (int i=0; i<num; i++) {
			System.out.println();			
		}
	}
	
	public static String printLine(int size) {
		String lines = "";
		for (int i=0; i<(size * 5); i++ ) {
			lines += "-";
		}
		return lines;
	}
	
	public static String printColumnNums(int size) {
		String columns = "       ";
		for (int i=0; i<(size); i++ ) {
			if (i < 10) {
				columns += " " + i + " ";
			} else {
				columns += " " + i;	
			}
		}
		return columns;
	}
	
	public static void printBoardFrame(char[][] array) {
		int index = 0;
		int size = array.length;
		
		System.out.println(Print.printLine(size));
		System.out.println(Print.printColumnNums(size));
		
		for (char[] row: array) {
			if (index < 10) {
				System.out.printf("  %d    |", index);
			} else {
				System.out.printf(" %d    |", index);
			}
			for (char dot:row) {
				System.out.print(dot);
				System.out.printf(" |");
				
			}
			System.out.printf("\n");
			index++;
		}
		System.out.println(Print.printLine(size));
	}
	
	public static void printInvalidInput() {
		System.out.println("-- Invalid input. Please enter a valid integer. --");
	}
	
	public static void printDuplication() {
		System.out.println("-- You've already used that. Please enter a different integer. --");
	}
	
	public static void printRevealFlag() {
		System.out.println("-- It was flagged coordinates. You can now reveal it. --");
	}
		

}
