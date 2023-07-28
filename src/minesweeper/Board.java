package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

// gird class to render grid every time with numbers
public class Board {
//	private final int num = 10;
	private final int boardSize;
	private final int edge;
	private int minesCount;
	
	/**
	 * mines will be 100
	 * revealed numbers will be 99
	 * flags will be 88
	 * */
	
	private int[][] minesCoords;
	private int[][] gameBoard;
	
	Board(int boardSize) {
		this.boardSize = boardSize;
		this.edge = this.boardSize - 1;
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
			if (this.minesCoords[x][y] != 100) {
				this.minesCoords[x][y] = 100;
				bombCounter ++;
			} else {
				continue;
			}
		}	
		
		System.out.printf("create mines: %d *****\n", bombCounter);
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
	
//	public void placeNum(int x, int y, int num) {
//		if (num == 0) {
//			// if the selected coord has 0 mine -> 99
//			// since the empty element is 0 
//			this.gameBoard[y][x] = 99;
//		} else {
//			this.gameBoard[y][x] = num;
//		}
//	}
	
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
	
	public String printLine() {
		String lines = "";
		for (int i=1; i<=(this.boardSize * 3.6); i++ ) {
			lines += "-";
		}
		return lines;
	}
	
	public String printColumnNums() {
		String columns = "       ";
		for (int i=1; i<=(this.boardSize); i++ ) {
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

		//88 || 99 should be handled together!
		
		int index = 1;
		
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
		if (x != this.edge && this.minesCoords[y][x+1] == 100) {
			minesCounter++;
		}
		if (y != 0 && this.minesCoords[y-1][x] == 100) {
			minesCounter++;
		}
		if (x != 0  && y != 0 && this.minesCoords[y-1][x-1] == 100) {
			minesCounter++;
		}
		if (x != this.edge  && y != 0 && this.minesCoords[y-1][x+1] == 100) {
			minesCounter++;
		}
		if (y != this.edge && this.minesCoords[y+1][x] == 100) {
			minesCounter++;
		}
		if (x != 0 && y != this.edge && this.minesCoords[y+1][x-1] == 100) {
			minesCounter++;
		}
		if (x != this.edge && y != this.edge && this.minesCoords[y+1][x+1] == 100) {
			minesCounter++;
		}
		
		return minesCounter;
	}
	
	public int revealEmptyCells(int x, int y) {
		
		int cleanCells = 0;
		
		

		if (this.minesCoords[y][x] == 0) {
			cleanCells++;
		}
		if (x != 0 && this.minesCoords[y][x-1] == 0) {
			cleanCells++;
		}
		if (x != 9 && this.minesCoords[y][x+1] == 0) {
			cleanCells++;
		}
		if (y != 0 && this.minesCoords[y-1][x] == 0) {
			cleanCells++;
		}
		if (x != 0  && y != 0 && this.minesCoords[y-1][x-1] == 0) {
			cleanCells++;
		}
		if (x != 9  && y != 0 && this.minesCoords[y-1][x+1] == 0) {
			cleanCells++;
		}
		if (y != 9 && this.minesCoords[y+1][x] == 0) {
			cleanCells++;
		}
		if (x != 0 && y != 9 && this.minesCoords[y+1][x-1] == 0) {
			cleanCells++;
		}
		if (x != 9 && y != 9 && this.minesCoords[y+1][x+1] == 0) {
			cleanCells++;
		}
		
		return cleanCells;
	}

}
