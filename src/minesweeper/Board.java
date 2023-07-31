package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

// gird class to render grid every time with numbers
public class Board {
//	private final int num = 10;
	private final int boardSize;
	private final int maxIndex;
	private int minesCount;
	private int flagsCount = 0;
	
	/**
	 * mines will be 100
	 * revealed numbers will be 99
	 * flags will be 88
	 * */
	
	private int[][] minesCoords;
	private int[][] gameBoard;
//	private boolean[][] revealed;
	
	Board(int boardSize) {
		this.boardSize = boardSize;
		this.maxIndex = this.boardSize - 1;
		
		this.setDifficulty();
		this.minesCoords = new int[this.boardSize][this.boardSize];
		this.gameBoard = new int[this.boardSize][this.boardSize];
//		this.revealed = new boolean[this.boardSize][this.boardSize];
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
				// if the selected coord has 0 mine -> 99
				// since the empty element is 0 
				this.gameBoard[y][x] = 99;
//				this.revealed[y][x] = true;
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
//				this.printBoard2();
//				this.printRevealedBoard();
			}
		}
	}
	
	public String printLine() {
		String lines = "";
		for (int i=0; i<(this.boardSize * 3.6); i++ ) {
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

		//88 || 99 should be handled together!
		
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
		
		System.out.printf("flags: %d, mines: %d \n", this.flagsCount, this.minesCount);
		
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
			System.out.println(results);
		}
		return results != "";
	}
	
//	public void revealArea(int x, int y) {
//		System.out.printf("x: %d, y: %d\n", x, y);
//		
//		if (outOfBounds(x, y)) return;
//		if (revealed[x][y]) return;
//		revealed[x][y] = true;
//		if (findNeighbour(x, y) != 0) return;
//		revealArea(x-1, y-1);
//		revealArea(x-1, y);
//		revealArea(x-1, y+1);
//		revealArea(x+1, y-1);
//		revealArea(x+1, y);
//		revealArea(x+1, y+1);
//		revealArea(x, y-1);
//		revealArea(x, y+1);
//	}
	
	public boolean outOfBounds(int x, int y) {
		return x < 0 || x > this.maxIndex || y < 0 || y > this.maxIndex;
	}
	
//	public void printBoard2() {
//		 for (int row = 0; row < this.maxIndex; row++) {
//		        for (int col = 0; col < this.maxIndex; col++) {
//		            if (revealed[row][col]) {
//		                // If the cell is revealed, print the actual value from the board array
//		                if (gameBoard[row][col] == -1) {
//		                    System.out.print(" * "); // '*' represents a mine
//		                } else {
//		                    System.out.print(" " + gameBoard[row][col] + " ");
//		                }
//		            } else {
//		                // If the cell is not revealed, print a symbol (e.g., 'X') to represent it
//		                System.out.print(" X ");
//		            }
//		        }
//		        System.out.println(); // Move to the next row after each inner loop
//		    }
//	}
	
	public int findNeighbour(int x, int y) {
		// move the 9 cells each of below
		int minesCounter = 0;
		
		for (int offsetX=-1; offsetX<=1; offsetX++) {
			for (int offsetY=-1; offsetY<=1; offsetY++) {
				if (this.outOfBounds(x + offsetX, y + offsetY)) continue;
				
				if (this.minesCoords[y + offsetY][x + offsetX] == 100) {
					minesCounter++;
				}					
//				if (this.gameBoard[y + offsetY][x + offsetX] == 0) {
//					this.revealArea(x + offsetX, y + offsetY);
//				}
//				
			}
		}

//		if (this.minesCoords[y][x] == 100) {
//			minesCounter++;
//		}
//		if (x != 0 && this.minesCoords[y][x-1] == 100) {
//			minesCounter++;
//		}
//		if (x != this.maxIndex && this.minesCoords[y][x+1] == 100) {
//			minesCounter++;
//		}
//		if (y != 0 && this.minesCoords[y-1][x] == 100) {
//			minesCounter++;
//		}
//		if (x != 0  && y != 0 && this.minesCoords[y-1][x-1] == 100) {
//			minesCounter++;
//		}
//		if (x != this.maxIndex  && y != 0 && this.minesCoords[y-1][x+1] == 100) {
//			minesCounter++;
//		}
//		if (y != this.maxIndex && this.minesCoords[y+1][x] == 100) {
//			minesCounter++;
//		}
//		if (x != 0 && y != this.maxIndex && this.minesCoords[y+1][x-1] == 100) {
//			minesCounter++;
//		}
//		if (x != this.maxIndex && y != this.maxIndex && this.minesCoords[y+1][x+1] == 100) {
//			minesCounter++;
//		}
		
		return minesCounter;
	}
	
	
}
