// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a 'go to jail' square on the Monopoly board. By landing on 
//              this square, players will be moved to the jail square and enter jail.

import java.lang.String;

public class GoToJailSquare extends BoardLocation
{
   // ****** CONSTRUCTORS ******
   
   public GoToJailSquare()
   // POST: Creates an instance of GoToJailSquare with address set to DEFAULT_ADDRESS
   {
      this(DEFAULT_ADDRESS);
   }

   public GoToJailSquare(int address)
   // PRE:  address >= 0
   // POST: Creates an instance of GoToJailSquare with data members address and name set to 
   //       address and "Go to Jail," respectively.
   {
      super(address, "Go To Jail");
   }
   
   // ****** ACCESSORS ******
      
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   // POST: FCTVAL == array of options player has upon landing on this GoToJailSquare, to be used
   //       in a menu in a user interface.
   {
      return new String[]{"Go to jail."};
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of GoToJailSquare
   {
      return super.toString() + " sends players to JailSquare";
   }
}