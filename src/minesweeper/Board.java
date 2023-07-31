package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class Board extends Minesweeper {

	private int minesCount;
	private int flagsCount = 0;
	
	/** LEGEND for boards **
	 * mines -> 100
	 * revealed numbers -> 99
	 * flags -> 88
	 * */
	
	private int[][] minesCoords;
	private int[][] gameBoard;
	
	Board(int boardSize) {
		
		this.boardSize = boardSize;
		this.maxIndex = this.boardSize - 1;
		
		this.setDifficulty();
		this.minesCoords = new int[this.boardSize][this.boardSize];
		this.gameBoard = new int[this.boardSize][this.boardSize];
		this.generateMinesCoords();
		
	}
	
	public int getRandomNum() {
		return (int) Math.round(Math.random()* (this.boardSize - 1));
	}
	
	public void setDifficulty() {
		switch(this.boardSize) {
		case 3:
		case 4:
			this.minesCount = 3;
			break;
		case 5:
		case 6:
		case 7:
		case 8:
			this.minesCount = 5;
			break;
		case 10:
		case 11:
			this.minesCount = 10;
			break;
		case 12:
		case 13:
			this.minesCount = 20;
			break;
		case 14:
			this.minesCount = 25;
			break;
		case 15:
			this.minesCount = 30;
			break;	
		}
	}
	
	public void generateMinesCoords () {
		int bombCounter = 0;
		
		while (bombCounter < this.minesCount) {			
			int x = getRandomNum();
			int y = getRandomNum();
			
			// mark the bomb on the board
			if (this.minesCoords[y][x] != 100) {
				this.minesCoords[y][x] = 100;
				bombCounter ++;
			} else {
				continue;
			}
		}	
		
		System.out.printf("--- %d Mines Hidden ---\n", bombCounter);
	}
	
	// getter
	public int[][] getMinesCoords() {
		return this.minesCoords;
	}
	
	public int[][] getGameBoard() {
		return this.gameBoard;
	}
	
	public int getMinesCount() {
		return this.minesCount;
	}
	
	public int getFlagsCount() {
		return this.flagsCount;
	}
	
	public boolean isMine (int x, int y, boolean isFlag) {
		if (isFlag) return false;
		if (this.minesCoords[y][x] != 100) {			
			return false;
		}
		return true;		
	}
	
	public void placeWhat(int x, int y, int num, boolean isFlag) {
		if (isFlag) {
			this.gameBoard[y][x] = 88;
		} else {
			
			if (num == 0) {
				// if the selected coords has no (0) mine -> set 99
				// since the empty element is 0 
				this.gameBoard[y][x] = 99;
			} else {
				this.gameBoard[y][x] = num;
			}
		}
	}
	
	
	public void printBoard(boolean isMine, boolean isFlag) {
		if (isMine) {
			// game done - print a board with mines
			this.printGameBoard(minesCoords, 100);
		} else {
			if (isFlag) {
				this.flagsCount++;
				this.printGameBoard(gameBoard, 88);
			} else {
				this.printGameBoard(gameBoard, 99);
			}
		}
	}
	
	public String printLine() {
		String lines = "";
		for (int i=0; i<(this.boardSize * 5); i++ ) {
			lines += "-";
		}
		return lines;
	}
	
	public String printColumnNums() {
		String columns = "       ";
		for (int i=0; i<(this.boardSize); i++ ) {
			if (i < 10) {
				columns += " " + i + " ";
			} else {
				columns += " " + i;	
			}
		}
		return columns;
	}
		
	
	public void printGameBoard(int[][] array, int num) {
		char mark = (num == 100) ? '*' :  '0' ;

		int index = 0;
		
		System.out.println(printLine());
		System.out.println(printColumnNums());
		
		for (int[] row: array) {
			if (index < 10) {
				System.out.printf("  %d    |", index);
			} else {
				System.out.printf(" %d    |", index);
			}
			for (int cell:row) {
				if (cell == 0) {
					System.out.printf(" ");
				} else if (cell == num) {
					if (cell == 88) {
						System.out.printf("%s", '@');						
					} else {
						
						System.out.printf("%s", mark);						
					}
				} else {	
					if (cell == 88) {
						System.out.printf("%s", '@');						
					} else if (cell == 99) {
						System.out.printf("%s", '0');
					} else {
						System.out.printf("%d", cell);						
					}
				}
				System.out.printf(" |");
				
			}
			System.out.printf("\n");
			index++;
		}
		System.out.println(printLine());
	}
	
	public boolean hasWon () {
		String results = "";
		
		// if the number of flags == the number of mines
		// loop the board and check they are placed in the same positions
		for (int i=0; i<this.boardSize; i++) {
			for (int j=0; j<this.boardSize; j++) {
				if (this.gameBoard[i][j] == 88) {
					if (this.minesCoords[i][j] == 100) {
						results += "t";
					} else {
						results = "";
					}
				}		
			}
		}
		return results != "";
	}
	
	
	public boolean outOfBounds(int x, int y) {
		return x < 0 || x > this.maxIndex || y < 0 || y > this.maxIndex;
	}
	
	
	public int findNeighbour(int x, int y) {
		// move the 9 cells each of below
		int minesCounter = 0;
		
		// -1 to 1 based on x & y
		for (int offsetX=-1; offsetX<=1; offsetX++) {
			for (int offsetY=-1; offsetY<=1; offsetY++) {
				if (this.outOfBounds(x + offsetX, y + offsetY)) continue;
				
				if (this.minesCoords[y + offsetY][x + offsetX] == 100) {
					minesCounter++;
				}					
			}
		}
		
		return minesCounter;
	}
	
	
}
