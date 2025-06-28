
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Used to display the contents of a game board
public class BlockDisplay implements KeyListener
{
	private static final Color BACKGROUND = Color.BLACK;

	private BoundedGrid<Block> board;
	private BoundedGrid<Block> nextTetradBoard;
	private JPanel[][] grid;
	private JFrame frame;
	private JFrame nextTetrad;
	private JPanel scorePanel;
	private int score = 0;
	private JLabel scoreLabel = new JLabel();
	private ArrowListener listener;

	// Constructs a new display for displaying the given board
	public BlockDisplay(BoundedGrid<Block> board)
	{
		this.board = board;
		grid = new JPanel[board.getNumRows()][board.getNumCols()];

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });

		//Wait until display has been drawn
        try
        {
        	while (frame == null || !frame.isVisible())
        		Thread.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.getContentPane().setLayout(new GridLayout(board.getNumRows(), board.getNumCols()));
        frame.addKeyListener(this);
        
		//Create each square component.
        for (int row = 0; row < grid.length; row++)
        	for (int col = 0; col < grid[row].length; col++)
        	{
				grid[row][col] = new JPanel();
				grid[row][col].setBackground(BACKGROUND);
				grid[row][col].setPreferredSize(new Dimension(20, 20));
				frame.getContentPane().add(grid[row][col]);
			}

		//Show the board
		showBlocks();
		
	
    	
		
	
        //Display the window.
        frame.pack();
    	
        scorePanel = new JPanel();
        scorePanel.add(scoreLabel);
		frame.setLayout(new BorderLayout());
    	frame.add(scorePanel, BorderLayout.SOUTH);
    	frame.setSize(205, 455);
    	
    	frame.setResizable(false);
        frame.setVisible(true);
        
        nextTetrad = new JFrame();
        nextTetrad.setLayout(new GridLayout(6, 4));
        for (int row = 0; row < 6; row++)
        	for (int col = 0; col < 4; col++)
        	{
				JPanel panel = new JPanel();
				panel.setBackground(BACKGROUND);
				panel.setPreferredSize(new Dimension(30,30));
				nextTetrad.getContentPane().add(panel);
			}
        nextTetrad.setLocation(300, 0);
        nextTetrad.setSize(300, 200);
        nextTetrad.setVisible(true);
        nextTetrad.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
    }

	//Redraws the board to include the pieces and border colors.
	public void showBlocks()
	{
		for (int row = 0; row < grid.length; row++)
			for (int col = 0; col < grid[row].length; col++)
			{
				Location loc = new Location(row, col);

				Block square = board.get(loc);

				if (square == null)
				{
					grid[row][col].setBackground(BACKGROUND);
					grid[row][col].setBorder(null);
				}
				else
				{
					grid[row][col].setBackground(square.getColor());
					grid[row][col].setBorder(BorderFactory.createLineBorder(BACKGROUND));
				}
			}
		scoreLabel.setText("Score: " + score);
	}
	public void showNextTetrad()
	{
		for (int row = 0; row < 6; row++)
			for (int col = 0; col < 4; col++)
			{
				Location loc = new Location(row, col);

				Block square = board.get(loc);

				if (square == null)
				{
					grid[row][col].setBackground(BACKGROUND);
					grid[row][col].setBorder(null);
				}
				else
				{
					grid[row][col].setBackground(square.getColor());
					grid[row][col].setBorder(BorderFactory.createLineBorder(BACKGROUND));
				}
			}
	}
	// Sets the title of the window.
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{
		if (listener == null)
			return;
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT)
			listener.leftPressed();
		else if (code == KeyEvent.VK_RIGHT)
			listener.rightPressed();
		else if (code == KeyEvent.VK_DOWN)
			listener.downPressed();
		else if (code == KeyEvent.VK_UP)
			listener.upPressed();
		else if (code == KeyEvent.VK_SPACE)
			listener.spacePressed();
	}

	public void setArrowListener(ArrowListener listener)
	{
		this.listener = listener;
	}
	public void addScore() {score += 100; showBlocks();}
	public int getCurrentSpeedMilliseconds()
	{
		if (score <= 100) return 1000;
		else if (score <= 500) return 950;
		else if (score <= 1000) return 900;
		else if (score <= 1500) return 850;
		else if (score <= 2000) return 800;
		else if (score <= 2500) return 750;
		else if (score <= 3000) return 700;
		else if (score <= 3500) return 650;
		else if (score <= 4000) return 600;
		else if (score <= 4500) return 550;
		else if (score <= 5000) return 500;
		else if (score <= 5500) return 450;
		else if (score <= 6000) return 400;
		else if (score <= 6500) return 350;
		else if (score <= 7000) return 300;
		else if (score <= 7500) return 250;
		else if (score <= 8000) return 200;
		else if (score <= 8500) return 150;
		else if (score <= 9000) return 100;
		else return 50;
	}

	// alistair 
	public void setScore(int newScore) {
		this.score = newScore;
		scoreLabel.setText("Score: " + score);
	}
}