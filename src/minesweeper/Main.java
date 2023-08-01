package minesweeper;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		String hack = args.length > 0 ? args[0].toLowerCase() : "";
		boolean isHacked = "hack".equals(hack);
		
		Run game = isHacked ? new Run(hack) : new Run();
		game.runGame();
		
	}

}
