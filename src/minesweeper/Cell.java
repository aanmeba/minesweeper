package minesweeper;

public class Cell extends Minesweeper {

	private boolean mine; // 100
	private boolean flag; // 88
	
	// reveal is true only if mine || flag is true, not neighbour
	private boolean reveal; // 99
	private int neighbour;
	
	public Cell () {
		this.resetCell();
	}
	
	public void resetCell() {
		this.mine = false;
		this.flag = false;
		this.reveal = false;
		this.neighbour = 0;
	}
	public boolean getMine() {
		return this.mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean getFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean getReveal() {
		return this.reveal;
	}

	public void setReveal(boolean reveal) {
		this.reveal = reveal;
	}

	public int getNeighbour() {
		return this.neighbour;
	}

	public void setNeighbour(int neighbour) {
		this.neighbour = neighbour;
	}
	
}
