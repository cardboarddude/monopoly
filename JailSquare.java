// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a jail square on the Monopoly board. This square is the
//              holding place for players who go to jail.

import java.lang.String;

public class JailSquare extends BoardLocation
{
   private static final int 
               DEFAULT_TURNS = 0;      // Default number of turns player is in jail
   private static final int
               DEFAULT_BAIL = 50;      // Default value of bail in Monopoly $
               
   private int bail;                   // Fee players must pay to leave jail if doubles are not
                                       //   rolled, bail > 0 in Monopoly dollars
   private int turnsInJail;            // Number of turns player is in jail, turnsInJail > 0
   
   // ****** CONSTRUCTORS ******
   
   public JailSquare(int address)
   // PRE:  address >= 0
   // POST: Creates an instance of JailSquare where data members address, turnsInJail and bail
   //       are set to address, DEFAULT_TURNS, and DEFAULT_BAIL, respectively.
   {
      this(address, DEFAULT_TURNS, DEFAULT_BAIL);
   }
   
   public JailSquare(int address, int turnsInJail, int bail)
   // PRE:  turnsInJail > 0 
   // POST: Creates an instance of JailSquare where data members name, turnsInJail, and bail
   //       are set to "Jail," turnsInJail, and bail, respectively.
   {
      super(address, "Jail");
      
      this.turnsInJail = turnsInJail;
      this.bail = bail;
   }
   
   // ****** ACCESSORS ******
   
   public int GetBail()
   // POST: FCTVAL == Fee to leave jail without rolling doubles in Monopoly dollars
   {
      return bail;
   }
   
   public int GetTurnsInJail()
   // POST: FCTVAL == The maximum number of turn that player is in jail for.
   {
      return turnsInJail;
   }
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   //       dice is initialized
   // POST: FCTVAL == array of options player has upon landing on this JailSquare, to be used
   //       in a menu in a user interface.
   {
      if (player.GetTurnsInJail() == 0)      // player is just passing through jail
      {
         return new String[]{"Continue."};
      }
      else                                   // player is in jail
      {
         if (player.GetTurnsInJail() >= turnsInJail)  // player has been in jail for the max
         {                                            //   number of turns in jail
            if (player.GetMoney() >= bail)            // Player has enough money to pay for bail
            {
               return new String[]{"You have been in jail for "
                              + player.GetTurnsInJail() + " turns. "
                              + "Pay bail of $" + bail +" to leave."};
            }
            else                                      // Player does not have enough money to 
            {                                         //   pay for bail
               return  new String[]{"You can't pay the bail of $" + bail 
                                  + ". Declare bankruptcy."};
            }
         }
         else                                         // player is in jail and didn't roll
         {                                            //   doubles
            return new String[]{"End turn."};
         }
      }
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of JailSquare
   {
      return super.toString() + " holds players for " + turnsInJail 
           + " turns or until a bail of $"
           + bail + " is paid";
   }
}