package minesweeper;

public class Validation {

	static boolean isValid;

	public static boolean validateInputRange(int inputNum) {
		if (inputNum >= 10 || inputNum < 0) {
			isValid = false;
		} else {			
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean validateOption(int inputNum) {
		if (inputNum == 1 || inputNum == 2) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
	
	public static void printValidationMessage() {
		if (isValid != true) {
			System.out.println("Invalid input. Please enter a valid integer.");
		}
	}
	
	public static void checkDuplication(int[][] array, int x, int y) {
		
		if (array[y][x] != 0) {
			System.out.println("You've already used that. Please enter a different integer.");
			isValid = false;
		} else {
			isValid = true;
		}
		
	}
	
	
}
