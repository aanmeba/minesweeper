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
		System.out.printf("setMine, %b\n", this.mine);
	}

	public boolean getFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
		System.out.printf("setFlag, %b\n", this.flag);
	}

	public boolean getReveal() {
		return this.reveal;
	}

	public void setReveal(boolean reveal) {
		this.reveal = reveal;
		System.out.printf("setReveal, %b\n", this.reveal);
	}

	public int getNeighbour() {
		return this.neighbour;
	}

	public void setNeighbour(int neighbour) {
		this.neighbour = neighbour;
		System.out.printf("setNeighbour, %d\n", this.neighbour);
	}
	
}
