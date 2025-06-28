import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Tetris.java  4/30/2014
 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ...
 *
 */

// Represents a Tetris game.
public class Tetris implements ArrowListener
{
	private BoundedGrid<Block> grid;	// The grid containing the Tetris pieces.
	private BlockDisplay display;		// Displays the grid.
	private Tetrad activeTetrad;		// The active Tetrad (Tetris Piece).
	
	private int score;


	// TO DO:
	// add more shapes and orientations to tetrads that spawn
	// make placed blocks different shades of blue
	// add animations for clears and stuff

	// Constructs a Tetris Game
	public Tetris()
	{
		// throw new RuntimeException("INSERT MISSING CODE HERE");
		grid = new BoundedGrid<Block>(8, 8);
		display = new BlockDisplay(grid);
		display.setTitle("Blocky Blast!");
		activeTetrad = new Tetrad(grid);
		display.showBlocks();
		display.setArrowListener(this);
		
		score = 0;
		// updateTitle();
	}

	public void upPressed(){
		activeTetrad.translate(-1, 0);
		display.showBlocks();
	} 

 	public void downPressed(){
		activeTetrad.translate(1, 0);
		display.showBlocks();
	} 

 	public void leftPressed(){
		activeTetrad.translate(0, -1);
		display.showBlocks();
	} 

 	public void rightPressed(){
		activeTetrad.translate(0, 1);
		display.showBlocks();
	}

	public void spacePressed(){
		// lock in grid position and switch to a new active tetrad
		// but first check if the spot is available
		if (activeTetrad.canPlace()) {
		activeTetrad.setColor(0, 100, 255); // 100 the best green value fr
		
		int linesCleared = 0;
		
		// check for clears
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (isCompletedRow(r) && isCompletedCol(c)) {
					clearRow(r);
					clearCol(c);
					// a wu
					linesCleared += 2;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isCompletedRow(i)) {
					clearRow(i);
					// a wu
					linesCleared++;
				}
				if (isCompletedCol(j)) {
					clearCol(j);
					// a wu
					linesCleared++;
				}
			}
		}

		activeTetrad = new Tetrad(grid);


		//add math for score
		if (linesCleared == 0) {
			score += 4;
		} else {
			score += linesCleared * 100;
		}
		
		// OR
		// score += linesCleared * 100 + 4;

	
		// updateTitle();

		display.showBlocks();
		display.setScore(score);
		}
	}

	// Play the Tetris Game
	public void play()
	{
		throw new RuntimeException("INSERT MISSING CODE HERE");
	}


	// Precondition:  0 <= row < number of rows
	// Postcondition: Returns true if every cell in the given row
	//                is occupied; false otherwise.
	private boolean isCompletedRow(int row)
	{
		// throw new RuntimeException("INSERT MISSING CODE HERE");
		for (int i = 0; i < 8; i++) {
			if (grid.get(new Location(row, i)) == null) {
				return false;
			}
		}
		return true;
	}

	private boolean isCompletedCol(int col)
	{
		// throw new RuntimeException("INSERT MISSING CODE HERE");
		for (int i = 0; i < 8; i++) {
			if (grid.get(new Location(i, col)) == null) {
				return false;
			}
		}
		return true;
	}

	// Precondition:  0 <= row < number of rows;
	//                The given row is full of blocks.
	// Postcondition: Every block in the given row has been removed, and
	//                every block above row has been moved down one row.
	private void clearRow(int row)
	{
		// throw new RuntimeException("INSERT MISSING CODE HERE");
		for (int i = 0; i < 8; i++) {
			if (grid.get(new Location(row, i)) != null) {
				grid.get(new Location(row, i)).removeSelfFromGrid();
			}
		}
	}

	private void clearCol(int col) {
		for (int i = 0; i < 8; i++) {
			if (grid.get(new Location(i, col)) != null) {
				grid.get(new Location(i, col)).removeSelfFromGrid();
			}
		}
	}

	// Postcondition: All completed rows have been cleared.
	private void clearCompletedRows()
	{
		throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Sleeps (suspends the active thread) for duration seconds.
	private void sleep(double duration)
	{
		final int MILLISECONDS_PER_SECOND = 1000;

		int milliseconds = (int)(duration * MILLISECONDS_PER_SECOND);

		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			System.err.println("Can't sleep!");
		}
	}

	// alistair
	// private void updateTitle() {
	// 	display.setTitle("Blocky Blast! | Score: " + score);
	// }


	// Creates and plays the Tetris game.
	public static void main(String[] args)
	{
		Tetris a = new Tetris();
	}
}
