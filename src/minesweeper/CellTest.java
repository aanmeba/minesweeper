package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CellTest {
	
	@Test
	public void Cell_InitializeValues() {
		Cell underTest = new Cell();
		boolean defaultValue = false;
		
		assertEquals(defaultValue, underTest.getMine());
		assertEquals(defaultValue, underTest.getFlag());
		assertEquals(defaultValue, underTest.getReveal());
		assertEquals(0, underTest.getNeighbour());
		
		
	}

}
