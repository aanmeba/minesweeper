package minesweeper;

public class Cell extends Minesweeper {

	private boolean reveal; 
	
  private boolean mine; 
	private boolean flag; 
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
		this.reveal = mine;
	}

	public boolean getFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
		this.reveal = flag;
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
		this.reveal = true;
	}
	
	
}
