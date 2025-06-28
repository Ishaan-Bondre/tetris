import java.awt.Color;

public class Block
{
    private BoundedGrid<Block> grid;
    private Location location;
    private Color color;

    //constructs a blue block, because blue is the greatest color ever! 
    public Block()
    {
        //color = Color.BLUE;
        grid = null;
        location = null;
    }

    //gets the color of this block
    public Color getColor()
    {
        return color;
    }

    //gets the color of this block to newColor.
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    //gets the grid of this block, or null if this block is not contained in a grid
    public BoundedGrid<Block> getGrid()
    {
        return grid;
    }

	//gets the location of this block, or null if this block is not contained in a grid
    public Location getLocation()
    {
        return location;
    }

	//removes this block from its grid
	//precondition:  this block is contained in a grid
   public void putSelfInGrid(BoundedGrid<Block> gr, Location loc)
    {
        if (grid != null) {
            throw new IllegalStateException(
                    "This block is already contained in a grid.");
        }
        
        Block block = gr.get(loc);
        if (block != null)
            block.removeSelfFromGrid();
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * Removes this block from its grid.
     * Precondition: This block is contained in a grid
     */
    public void removeSelfFromGrid()
    {
        if (grid == null)
            throw new IllegalStateException(
                    "This block is not contained in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different block at location "
                            + location + ".");

        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Moves this block to a new location. If there is another block at the
     * given location, it is removed.
     * Precondition: (1) This block is contained in a grid (2)
     * newLocation is valid in the grid of this block
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (grid == null)
            throw new IllegalStateException("This block is not in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different block at location "
                            + location + ".");
        if (!grid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        grid.remove(location);
        Block other = grid.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        grid.put(location, this);
    }

	//returns a string with the location and color of this block
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }

}