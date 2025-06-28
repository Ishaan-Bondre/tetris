/**
 * Tetrad.java  4/30/2014
 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ...
 *
 */

 import java.awt.Color;
 import java.util.ArrayList;
 
 // Represents a Tetris piece.
 public class Tetrad
 {
     private Block[] blocks; // The blocks for the piece.
 
     private ArrayList<Location> savedBlocks = new ArrayList<>();
 
     // Constructs a Tetrad.
     public Tetrad(BoundedGrid<Block> grid)
 {
     blocks = new Block[4];
     Color tempColor = new Color(18, 219, 71);
     for (int i = 0; i < blocks.length; i++) {
         blocks[i] = new Block();
         blocks[i].setColor(tempColor);
     }
 
     // Shapes centered around (0,0)
     Location[][] shapes = {
        { new Location(0, -1), new Location(0, 0), new Location(0, 1), new Location(0, 2) },
        { new Location(0, -1), new Location(0, 0), new Location(0, 1), new Location(1, 1) },
        { new Location(0, 0), new Location(0, 1), new Location(1, 0), new Location(1, 1) },  
        { new Location(0, -1), new Location(0, 0), new Location(0, 1), new Location(1, 0) },
        { new Location(0, 0), new Location(0, 1), new Location(1, -1), new Location(1, 0) },
        { new Location(0, -1), new Location(0, 0), new Location(1, 0), new Location(1, 1) },
		{ new Location(0, 2), new Location(1,0), new Location(1,1), new Location(1,2)}  
     };
 
     int shapeIndex = (int) (Math.random() * shapes.length);
     Location[] relativeLocs = shapes[shapeIndex];
 
     // Rotate randomly (90°, 180°, or 270°) — never 0°
     int rotations = (int) (Math.random() * 4); // 1, 2, or 3
     for (int r = 0; r < rotations; r++) {
         for (int i = 0; i < relativeLocs.length; i++) {
             int row = relativeLocs[i].getRow();
             int col = relativeLocs[i].getCol();
             relativeLocs[i] = new Location(-col, row); // rotate 90° clockwise
         }
     }
 
     // Center horizontally
     int midCol = grid.getNumCols() / 2;
     Location[] tempLocs = new Location[4];
     for (int i = 0; i < 4; i++) {
         int r = relativeLocs[i].getRow();
         int c = relativeLocs[i].getCol() + midCol;
         tempLocs[i] = new Location(r, c);
     }
 
     // If any block is above the top (row < 0), shift the entire piece down
     int minRow = Integer.MAX_VALUE;
     for (Location loc : tempLocs) {
         minRow = Math.min(minRow, loc.getRow());
     }
     if (minRow < 0) {
         for (int i = 0; i < 4; i++) {
             tempLocs[i] = new Location(tempLocs[i].getRow() - minRow, tempLocs[i].getCol());
         }
     }
 
     // Place blocks (ok to overlap)
     for (int a = 0; a < 4; a++) {
         if (grid.get(tempLocs[a]) != null) {
             savedBlocks.add(tempLocs[a]);
         }
         blocks[a].putSelfInGrid(grid, tempLocs[a]);
     }
     addToLocations(grid, tempLocs);
 
     // Restore saved blocks
     for (int k = savedBlocks.size() - 1; k >= 0; k--) {
         if (grid.get(savedBlocks.get(k)) == null) {
             Block yippee = new Block();
             yippee.setColor(new Color(0, 100, 255));
             yippee.putSelfInGrid(grid, savedBlocks.get(k));
             savedBlocks.remove(k);
         }
     }
 
     if (savedBlocks.size() != 0) {
         setColor(255, 0, 0); // red if overlap happened
     }
 }
 
 
 
 
     // Postcondition: Attempts to move this tetrad deltaRow rows down and
     //                      deltaCol columns to the right, if those positions are
     //                      valid and empty.
     //                      Returns true if successful and false otherwise.
     public boolean translate(int deltaRow, int deltaCol)
     {
         Location[] newLocs = new Location[4];
         BoundedGrid<Block> theGrid = blocks[0].getGrid();
         for (int i = 0; i < 4; i++) {
             newLocs[i] = new Location(blocks[i].getLocation().getRow() + deltaRow, blocks[i].getLocation().getCol() + deltaCol);
             if ((newLocs[i].getRow() < 0) || (newLocs[i].getRow() > theGrid.getNumRows()-1) || (newLocs[i].getCol() < 0) || (newLocs[i].getCol() > theGrid.getNumCols()-1)) {
                 return false;
             }
             if ((blocks[i].getGrid().get(newLocs[i]) != null) && (blocks[i].getGrid().get(newLocs[i]).getColor().equals(new Color(0, 100, 255)))) {
                 savedBlocks.add(newLocs[i]);
             }
             // blocks[i].removeSelfFromGrid();
         }
         // shoutout to my goat Liam Holzer for fixing the movement glitch
         for (int k = 0; k < 4; k++) {
             blocks[k].removeSelfFromGrid();
         }
         // GIOSAT
         for (int j = 0; j < 4; j++) {
             blocks[j].putSelfInGrid(theGrid, newLocs[j]);
         }
         for (int k = savedBlocks.size()-1; k >= 0; k--) {
             if (theGrid.get(savedBlocks.get(k)) == null) {
                 Block yippee = new Block();
                 yippee.setColor(new Color(0, 100, 255));
                 yippee.putSelfInGrid(theGrid, savedBlocks.get(k));
                 savedBlocks.remove(k);
             }
         }
 
         if (savedBlocks.size() == 0) {
             setColor(18, 219, 71);
         } else {
             setColor(255, 0, 0);
         }
 
         return true;
 
         // FIX MOVEMENT GLITCH (w bounds) is finished
     }
 
     public boolean rotate()
 {
     BoundedGrid<Block> grid = blocks[0].getGrid();
     Location[] newLocs = new Location[4];
     Location center = blocks[1].getLocation();
 
     for (int i = 0; i < 4; i++) {
         int row = blocks[i].getLocation().getRow();
         int col = blocks[i].getLocation().getCol();
         int dRow = row - center.getRow();
         int dCol = col - center.getCol();
         int newRow = center.getRow() - dCol;
         int newCol = center.getCol() + dRow;
         newLocs[i] = new Location(newRow, newCol);
 
         if (newRow < 0 || newRow >= grid.getNumRows() || newCol < 0 || newCol >= grid.getNumCols()) {
             return false;
         }
         Block b = grid.get(newLocs[i]);
         if (b != null && !b.getColor().equals(new Color(0, 100, 255))) {
             return false;
         }
         if (b != null && b.getColor().equals(new Color(0, 100, 255))) {
             savedBlocks.add(newLocs[i]);
         }
     }
 
     removeBlocks();
     addToLocations(grid, newLocs);
 
     for (int k = savedBlocks.size()-1; k >= 0; k--) {
         if (grid.get(savedBlocks.get(k)) == null) {
             Block b = new Block();
             b.setColor(new Color(0, 100, 255));
             b.putSelfInGrid(grid, savedBlocks.get(k));
             savedBlocks.remove(k);
         }
     }
 
     if (savedBlocks.size() == 0) {
         setColor(18, 219, 71);
     } else {
         setColor(255, 0, 0);
     }
 
     return true;
 }
 
     private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
     {
         for (int i = 0; i < locs.length; i++) {
             grid.put(locs[i], blocks[i]);
         }
 
 
     }
 
     private Location[] removeBlocks()
     {
         Location[] result = new Location[4];
         for (int i = 0; i < blocks.length; i++) {
             result[i] = blocks[i].getLocation();
             blocks[i].removeSelfFromGrid();
         }
 
         return result;
     }
 
     private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs)
     {
         for (int i = 0; i < locs.length; i++) {
             if (grid.get(locs[i]) != null) {
                 return false;
             }
         }
         return true;
 
     }
 
     public void setColor(int r, int g, int b) {
         for (int i = 0; i < 4; i++) {
             blocks[i].setColor(new Color(r, g, b));
         }
     }
 
 
     public boolean canPlace() {
         if (savedBlocks.size() == 0) {
             return true;
         } else {
             return false;
         }
     }
 }
