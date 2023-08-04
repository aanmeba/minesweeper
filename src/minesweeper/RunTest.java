package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RunTest {
	
	Run underTest;
	
	@Test
	public void Run_InitializeCorrectly() {
		underTest = new Run();
		assertEquals(true, underTest.getIsGameRunning());
	}
	
	@Test
	public void RunWithArg_InitializeCorrectly() {
		underTest = new Run("hack");
		assertEquals(true, underTest.getIsGameRunning());
		assertEquals(true, underTest.getIsHacked());
	}
	
	@Test
	public void finishGame_StopRunningGame() {
		underTest = new Run();
		boolean done = underTest.finishGame();
		assertEquals(false, done);
	}	

}
