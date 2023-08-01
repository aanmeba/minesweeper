package minesweeper;


public class Board extends Minesweeper {

	private int minesCount;
	private int flagsCount = 0;
		
	
	/** LEGEND for boards **
	 * mines -> 100
	 * revealed numbers -> 99
	 * flags -> 88
	 * */
	
	private Cell[][] minesCoords;
	private Cell[][] gameBoard;
//	private int[][] minesCoords;
//	private int[][] gameBoard;
	
	Board(int boardSize) {
		
		this.boardSize = boardSize;
		this.maxIndex = this.boardSize - 1;
		
		this.setDifficulty();
		this.minesCoords = new Cell[this.boardSize][this.boardSize];
		this.gameBoard = new Cell[this.boardSize][this.boardSize];
		
		for (int x=0; x<this.boardSize; x++) {
			for (int y=0; y<this.boardSize; y++) {
				this.minesCoords[y][x] = new Cell();
				this.gameBoard[y][x] = new Cell();
			}
		}
		
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
			if (!this.minesCoords[y][x].getMine()) {
				this.minesCoords[y][x].setMine(true);
				this.minesCoords[y][x].setReveal(true); // can I use setReveal for mines baord?
				System.out.printf("y: %d, x: %d\n", y, x);
				bombCounter ++;
			} else {
				continue;
			}
		}	
		
		System.out.printf("-- %d Mines Hidden --\n", bombCounter);
	}
	
	// getter
	public Cell[][] getMinesCoords() {
		return this.minesCoords;
	}
	
	public Cell[][] getGameBoard() {
		return this.gameBoard;
	}
	
	public int getMinesCount() {
		return this.minesCount;
	}
	
	public int getFlagsCount() {
		return this.flagsCount;
	}
	
	public void setFlagsCount() {
		this.flagsCount--;
	}
	
	public boolean isMine (int x, int y, boolean isFlag) {
		if (isFlag) return false;
		return this.minesCoords[y][x].getMine();
//		if (!this.minesCoords[y][x].getMine()) {			
//			return false;
//		}
//		return true;		
	}
	
	public void placeWhat(int x, int y, int num, boolean isFlag) {
		if (isFlag) {
			this.gameBoard[y][x].setFlag(isFlag);;
			this.gameBoard[y][x].setReveal(true);;
		} else {
			
			if (num == 0) {
				// if the selected coords has no (0) mine -> set 99
				// since the empty element is 0 
				this.gameBoard[y][x].setReveal(true);;
			} else {
				this.gameBoard[y][x].setNeighbour(num);
				this.gameBoard[y][x].setReveal(true);;
			}
		}
	}
	
	
	public void printBoard(boolean isMine, boolean isFlag) {
		if (isMine) {
			// game done - print a board with mines
			this.printGameBoard(minesCoords, "mine"); // mine 100
		} else {
			if (isFlag) {
				this.flagsCount++;
				this.printGameBoard(gameBoard, "flag"); // flag 88
			} else {
				this.printGameBoard(gameBoard, "reveal"); // reveal 99
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
		
	
	public void printGameBoard(Cell[][] array, String type) {
		char mark = (type == "mine") ? '*' :  '0' ;
		// num -> 88, 99, 100 // mark -> 99 OR 100

		int index = 0;
		
		System.out.println(printLine());
		System.out.println(printColumnNums());
		
		for (Cell[] row: array) {
			if (index < 10) {
				System.out.printf("  %d    |", index);
			} else {
				System.out.printf(" %d    |", index);
			}
			for (Cell dot:row) {
				
				if (!dot.getReveal()) { // 99
					System.out.printf(" ");
					
				} else if (dot.getFlag() || dot.getMine()) {
					if (dot.getFlag()) { // 88 || 100
						System.out.printf("%s", '@');						
					} else {	
						System.out.printf("%s", mark);	// *?					
					}
				} else {	
					if (dot.getFlag()) {
						System.out.printf("%s", '@');						
					} else if (dot.getNeighbour() == 0) {
						System.out.printf("%s", '0');
					} else {
						System.out.printf("%d", dot.getNeighbour()); // already revealed					
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
				if (this.gameBoard[i][j].getFlag()) {
					if (this.minesCoords[i][j].getMine()) {
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
				
				if (this.minesCoords[y + offsetY][x + offsetX].getMine()) {
					minesCounter++;
				}					
			}
		}
		
		return minesCounter;
	}
	
//	public boolean removeFlag(Cell[][] board, Cell[][] mines, int x, int y, boolean isFlag) {
//		if (board[y][x].getFlag() && isFlag) {
//			board[y][x].setFlag(false); 
//			this.flagsCount--;
//			
//			System.out.println("-- It was flagged coordinates. You can now reveal it. --");
//			
//			if (mines[y][x].getMine()) {
//				this.isMine = true;				
//			} else {
//				board[y][x].setReveal(true);
//			}
////			isValid = true;
//		
//			return true;
//		} else {
//			return false;
//		}
//		
//	}
	
	
}
