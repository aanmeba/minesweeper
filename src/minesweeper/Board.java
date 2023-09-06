package minesweeper;


public class Board extends Minesweeper {

	private int minesCount;
	private int flagsCount = 0;
		
	private Cell[][] minesCoords;
	private Cell[][] gameBoard;
	private char[][] calculatedBoard;
	
	Board(int boardSize) {
		
		this.boardSize = boardSize;
		this.maxIndex = this.boardSize - 1;
		
		this.setDifficulty();
		this.minesCoords = new Cell[this.boardSize][this.boardSize];
		this.gameBoard = new Cell[this.boardSize][this.boardSize];
		this.calculatedBoard = new char[this.boardSize][this.boardSize];
		
		this.initialiseBoard();		
		this.generateMinesCoords();
	}
	
	public void initialiseBoard() {
		for (int x=0; x<this.boardSize; x++) {
			for (int y=0; y<this.boardSize; y++) {
				this.minesCoords[y][x] = new Cell();
				this.gameBoard[y][x] = new Cell();
				this.calculatedBoard[y][x] = ' ';
			}
		}
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
			
			// mark mines on the board
			if (!this.minesCoords[y][x].getMine()) {
				this.minesCoords[y][x].setMine(true);
				bombCounter ++;
			} else {
				continue;
			}
		}	
		
		System.out.printf("-- %d Mines Hidden --\n", bombCounter);
	}
	
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
	
	public int getMaxIndex() {
		return this.maxIndex;
	}
	
	public int getBoardSize() {
		return this.boardSize;
	}	

	public char[][] getCalculatedBoard() {
		return this.calculatedBoard;
	}
	
	
	public boolean isMine (int x, int y, boolean isFlag) {
		if (isFlag) return false;
		return this.minesCoords[y][x].getMine();	
	}
	
	public void placeWhat(int x, int y, int num, boolean isFlag) {
		
		if (isFlag) {
			this.gameBoard[y][x].setFlag(isFlag);
		} else {
			
//			if (num == 0) {
//			} else {
				this.gameBoard[y][x].setNeighbour(num);
//			}
		}
	}
	
	public Cell[][] whichBoard(boolean isMine) {
		if (isMine) return this.minesCoords; 
		return this.gameBoard;
	}
	
	public String whichType(boolean isMine, boolean isFlag) {
		if (isMine) return "mine"; 
		if (isFlag) {
			this.flagsCount++;
			return "flag"; 
		} else {
			return "reveal"; 
		}
	
	}
	
	
	public void calculateCellsInBoard(Cell[][] array, String type) {
		char mark = (type == "mine") ? '*' :  '0' ;
		
		for (int y=0; y<array.length; y++) {			
			for (int x=0; x<array.length; x++) {
				if (!array[y][x].getReveal()) { 
					
					this.calculatedBoard[y][x] = ' ';
					
				} else if (array[y][x].getFlag() || array[y][x].getMine()) {
					
					if (array[y][x].getFlag()) {
						this.calculatedBoard[y][x] = '@';				
					} else {
						this.calculatedBoard[y][x] = mark;			
					}
					
				} else {	
					
					if (array[y][x].getFlag()) {
						this.calculatedBoard[y][x] = '@';					
					
					} else if (array[y][x].getNeighbour() == 0) {
						this.calculatedBoard[y][x] = '0';
					
					} else {						
						int num = array[y][x].getNeighbour();
						this.calculatedBoard[y][x] = (char) (num + '0');					
					}
				}	
			}
		}
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
	
}
