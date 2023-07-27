package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

// gird class to render grid every time with numbers
public class Board {
	private final int num = 10;
	
	/**
	 * mines will be 100
	 * revealed numbers will be 99
	 * flags will be 88
	 * */
	
	private int[][] minesCoords = new int[this.num][this.num];
	private int[][] gameBoard = new int[this.num][this.num];
	
	Board() {
		this.generateMinesCoords();
	}
	
	public int getRandomNum() {
		return (int) Math.round(Math.random()* (this.num - 1));
	}
	
	public void generateMinesCoords () {
		int bombCounter = 0;
		
		while (bombCounter < 10) {			
			int x = getRandomNum();
			int y = getRandomNum();
			
			// mark the bomb on the board
			if (this.minesCoords[x][y] != 100) {
				this.minesCoords[x][y] = 100;
				bombCounter ++;
			} else {
				continue;
			}
		}		
	}
	
	// getter
	public int[][] getMinesCoords() {
		return this.minesCoords;
	}
	
	public int[][] getGameBoard() {
		return this.gameBoard;
	}
	
	public boolean isMine (int x, int y) {
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
				// if the selected coord has 0 mine -> 99
				// since the empty element is 0 
				this.gameBoard[y][x] = 99;
			} else {
				this.gameBoard[y][x] = num;
			}
		}
	}
	
	public void placeNum(int x, int y, int num) {
		if (num == 0) {
			// if the selected coord has 0 mine -> 99
			// since the empty element is 0 
			this.gameBoard[y][x] = 99;
		} else {
			this.gameBoard[y][x] = num;
		}
	}
	
	public void printBoard(boolean isDone, boolean isFlag) {
		if (isDone) {
			this.printGameBoard(minesCoords, 100);
		} else {
			if (isFlag) {
				this.printGameBoard(gameBoard, 88);
			} else {
				this.printGameBoard(gameBoard, 99);
			}
		}
	}
	
	public void printGameBoard(int[][] array, int num) {
		 char mark = (num == 100) ? '*' :  '0' ;
//		char mark;
//		if (num == 100) {
//			mark = '*';
//		} else if (num == 99) {
//			mark = '0';
//		} else if (num == 88) {
//			mark = '@';
//		} else {
//			mark = 'x';
//		}
		
		//88 || 99 should be handled together!
		
		int index = 0;
		
		System.out.println("-------------------------");
		System.out.println("    0 1 2 3 4 5 6 7 8 9");
		
		for (int[] row: array) {
			System.out.printf("%d  |", index);
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
				System.out.printf("|");
				
			}
			System.out.printf("\n");
			index++;
		}
		System.out.println("-------------------------");
	}
	
	
	public int findNeighbour(int x, int y) {
		// move the 9 cells each of below
		// if y, x, x-1, x+1
		// if y-1, x, x-1, x+1
		// if y+1, x, x-1, x+1
		
		int minesCounter = 0;
		
		// y == 0, 9 && x == 0, 9
		// if x == 0, x, x+1
		// if x == 9, x, x-1
		// if y == 0, y, y-1
		// if y == 9, y, y+1

		if (this.minesCoords[y][x] == 100) {
			minesCounter++;
		}
		if (x != 0 && this.minesCoords[y][x-1] == 100) {
			minesCounter++;
		}
		if (x != 9 && this.minesCoords[y][x+1] == 100) {
			minesCounter++;
		}
		if (y != 0 && this.minesCoords[y-1][x] == 100) {
			minesCounter++;
		}
		if (x != 0  && y != 0 && this.minesCoords[y-1][x-1] == 100) {
			minesCounter++;
		}
		if (x != 9  && y != 0 && this.minesCoords[y-1][x+1] == 100) {
			minesCounter++;
		}
		if (y != 9 && this.minesCoords[y+1][x] == 100) {
			minesCounter++;
		}
		if (x != 0 && y != 9 && this.minesCoords[y+1][x-1] == 100) {
			minesCounter++;
		}
		if (x != 9 && y != 9 && this.minesCoords[y+1][x+1] == 100) {
			minesCounter++;
		}
		
		return minesCounter;
	}

}
