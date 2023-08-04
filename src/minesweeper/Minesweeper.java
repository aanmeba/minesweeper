package minesweeper;

abstract class Minesweeper {
	protected final int minBoardSize = 5;
	protected final int maxBoardSize = 15;
	protected int maxIndex;
	protected int boardSize;
	protected boolean isMine;
	
	public void MineSweeper(int boardSize) {
		this.boardSize = boardSize;
		this.maxIndex = boardSize - 1;
	}
	
}
