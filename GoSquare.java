// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a Go square on the Monopoly board. By landing or passing this
//              square, players will collect money. By default this is the starting location of
//              the Monopoly game.

import java.lang.String;

public class GoSquare extends BoardLocation
{
   private static final int 
               DEFAULT_REWARD = 0;     // Default value of reward in Monopoly Dollars
   private int reward;                 // Money gained by landing on this GoSquare,
                                       //   reward >= 0 in Monopoly dollars
   // ****** CONSTRUCTORS ******
   
   public GoSquare()
   // POST: Creates an instance of GoSquare where address is set to DEFAULT_ADDRESS and reward 
   //       is set to DEFAULT_REWARD.
   {
      this(DEFAULT_ADDRESS, DEFAULT_REWARD);
   }
   
   public GoSquare(int address, int reward)
   // PRE:  address >= 0
   //       reward >= 0 in Monopoly dollars
   // POST: Creates an instance of GoSquare where data members address, name, and reward are 
   //       set to address, "Go," and reward, respectively.
   {
      super(address, "Go");
      
      this.reward = reward;
   }
   
   // ****** ACCESSORS ******
   
   public int GetReward()
   // POST: FCTVAL == Amount of money player earns when landing on this square in Monopoly
   //       dollars
   {
      return reward;
   }
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   // POST: FCTVAL == array of options player has upon landing on this GoSquare, to be used
   //       in a menu in a user interface.
   {
      if (reward == 0)                       // player does not collect any money
      {
         return new String[]{"Continue."};
      }
      else                                   // player collects money
      {
         return new String[]{"Collect a reward of $" + reward + "."};
      }
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of GoSquare
   {
      return super.toString() + " gives players $" + reward + " when landed on or passed.";
   }
}