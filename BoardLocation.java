// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a square on the Monopoly board.

import java.lang.String;

public abstract class BoardLocation
{
   protected static final int
               DEFAULT_ADDRESS = 0;   // Default value of address
   protected int address;             // Index cooresponding to the physical location of this
                                      //   BoardLocation on the board, address >= 0
   protected String name;             // Title of the square on the Monopoly board, e.g. "Marvin
                                      //   Gardens"
   // ****** CONSTRUCTORS ******
   
   public BoardLocation()
   // POST: Creates an instance of BoardLocation with data member address set to 
   //       DEFAULT_ADDRESS and data member name set to a blank String, respectively.
   {
      this(DEFAULT_ADDRESS, " ");
   }
   
   public BoardLocation(int address, String name)
   // POST: Creates an instance of BoardLocation with data member address set to address and
   //       data member name set to name, respectively.
   {
      this.address = address;
      this.name = name;
   }
   
   // ****** ACCESSORS ******
   
   public int GetAddress()
   // POST: FCTVAL == the position of this BoardLocation 
   {
      return address;
   }
   
   public String GetName()
   // POST: FCTVAL == The title of this square on the Monopoly board, e.g. "Marvin Gardens"
   {
      return name;
   }
   
   abstract String[] getPossibleActions(Player player, Dice dice);
   // PRE:  player is initialized
   //       dice is initialized
   // POST: FCTVAL == array of options player has upon landing on this space, to be used in a 
   //       menu in a user interface
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of BoardLocation
   {
      return name + " at address " + address;
   }
}