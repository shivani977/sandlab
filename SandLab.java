
//30 January, 2025
import java.awt.*;
import java.util.*;

public class SandLab
{
    public static void main(String[] args)
    {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }

    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int SAND = 2;
    public static final int WATER = 3;
    public static final int FIRE = 4;
    public static final int WOOD = 5;
    public static final int ASH = 6;
    public static final int ACID = 7;

    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;

    public SandLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[8];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        names[SAND] = "Sand";
        names[WATER] = "Water";
        names[FIRE] = "Fire";
        names[WOOD] = "Wood";
        names[ASH] = "Ash";
        names[ACID] = "Acid";
        display = new SandDisplay("Falling Sand", numRows, numCols, names);

        grid = new int[numRows][numCols];
    }

    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool)
    {
        grid[row][col] = tool;
    }

    //copies each element of grid into the display
    public void updateDisplay()
    {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == EMPTY) {
                    display.setColor(row, col, new Color(0, 0, 0)); // Black
                } else if (grid[row][col] == METAL) {

                    display.setColor(row, col, new Color(128, 128, 128)); // Gray
                }else if (grid[row][col] == SAND) {

                    display.setColor(row, col, new Color(255, 213, 0)); // yellow
                }
                else if (grid[row][col] == WATER) {

                    display.setColor(row, col, new Color(71, 111, 198)); // blue
                }
                else if (grid[row][col] == FIRE) {

                    display.setColor(row, col, new Color(255, 149, 0)); // orange
                }
                else if (grid[row][col] == WOOD) {

                    display.setColor(row, col, new Color(101, 59, 2)); // brown
                }
                else if (grid[row][col] == ASH) {

                    display.setColor(row, col, new Color(200, 200, 200)); // grey
                } else if (grid[row][col] == ACID){

                    display.setColor(row, col, new Color(211, 255, 39)); // green
                }

               // space for more particle types
            }
        }
    }
    //moveParticle function - for code resuse
    //moves a specific particle a specified amount in a specified direction
    private boolean moveParticle(int row, int col, int deltaRow, int deltaCol, int type) {
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;

        if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[row].length && grid[newRow][newCol] == EMPTY) {
            grid[newRow][newCol] = type;
            grid[row][col] = EMPTY;
            return true; // Movement successful
        }
        return false; // Movement not possible
    }
    private boolean moveAcid(int row, int col, int deltaRow, int deltaCol, int type) {
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;

        // Check if the new position is within bounds
        if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[row].length) {
            // If the adjacent space is not FIRE, ACID, or METAL (immune to acid), then move the acid
            if (grid[newRow][newCol] != FIRE && grid[newRow][newCol] != ACID && grid[newRow][newCol] != METAL) {
                grid[newRow][newCol] = type;
                grid[row][col] = EMPTY;
                return true; // Movement successful
            }
        }
        return false; // Movement not possible
    }


    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
        int row = (int)(Math.random() * grid.length);
        int col = (int)(Math.random() * grid[row].length);


        if (grid[row][col] == SAND) {
            // Sand can move down into an empty space or swap with water
            if (row + 1 < grid.length) {
                if (grid[row + 1][col] == EMPTY) {
                    moveParticle(row, col, 1, 0, SAND);
                } else if (grid[row + 1][col] == WATER) {
                    // Swap sand with water
                    grid[row + 1][col] = SAND;
                    grid[row][col] = WATER;
                }
            }

        } else if (grid[row][col] == WATER) {
            // random direction generated
            int direction = (int) (Math.random() * 3);
            if (direction == 0) { // Down
                moveParticle(row, col, 1, 0, WATER);
            } else if (direction == 1) { // Left
                moveParticle(row, col, 0, -1, WATER);
            } else { // Right
                moveParticle(row, col, 0, 1, WATER);
            }
        } else if (grid[row][col] == FIRE) {
            // if fire is next to wood, turns into fire
            if (row + 1 < grid.length && grid[row + 1][col] == WOOD){
                grid[row + 1][col] = FIRE;
            }
            if (row - 1 >= 0 && grid[row - 1][col] == WOOD){
                grid[row - 1][col] = FIRE;
            }
            if (col + 1 < grid[row].length && grid[row][col + 1] == WOOD){
                grid[row][col + 1] = FIRE;
            }
            if (col - 1 >= 0 && grid[row][col - 1] == WOOD){
                grid[row][col - 1] = FIRE;
            }

            // Fire extinguishes and turns to EMPTY after a few steps
            if (Math.random() < 0.005) grid[row][col] = EMPTY;
            //0.05 value - so that fire can spread a little but is not permanent
        } else if (grid[row][col] == WOOD) {
            // wood burns if next to fire and turns into ash
            if ((row + 1 < grid.length && grid[row + 1][col] == FIRE) ||
                    (row - 1 >= 0 && grid[row - 1][col] == FIRE) ||
                    (col + 1 < grid[row].length && grid[row][col + 1] == FIRE) ||
                    (col - 1 >= 0 && grid[row][col - 1] == FIRE)) {
                grid[row][col] = ASH;
            }
        } else if (grid[row][col] == ASH) {
            // ash acts like sand, falls down
            moveParticle(row, col, 1, 0, ASH);
        } else if( grid[row][col] == ACID) {
            int direction = (int) (Math.random() * 3);

            if (direction == 0) { // Down
                moveAcid(row, col, 1, 0, ACID);
            } else if (direction == 1) { // Left
                moveAcid(row, col, 0, -1, ACID);
            } else { // Right
                moveAcid(row, col, 0, 1, ACID);
            }


        }
    }

    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
                step();
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null)  //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
        }
    }
}
