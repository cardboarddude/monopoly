// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a free parking square on the Monopoly board. By landing on
//              this square, player has the potential to win money.

import java.lang.String;

public class FreeParkingSquare extends BoardLocation
{
   private static final int 
               DEFAULT_REWARD = 0;     // Default value of reward in Monopoly Dollars
   private int reward;                 // Money gained by landing on this FreeParkingSquare,
                                       //   reward >= 0 in Monopoly dollars
   // ****** CONSTRUCTORS ******
   
   public FreeParkingSquare()
   // POST: Creates an instance of FreeParkingSquare where data members address and reward are
   //       set to DEFAULT_ADDRESS and DEFAULT_REWARD, respectively.
   {
      this(DEFAULT_ADDRESS, DEFAULT_REWARD);
   }
   
   public FreeParkingSquare(int address)
   // PRE:  address >= 0
   // POST: Creates an instance of FreeParkingSquare where data members address and reward are
   //       set to address and DEFAULT_REWARD, respectively.
   {
      this(address, DEFAULT_REWARD);
   }
   
   public FreeParkingSquare(int address, int reward)
   // PRE:  address >= 0
   //       reward > 0 in Monopoly dollars
   // POST: Creates an instance of FreeParkingSquare where data members address, name, and 
   //       reward are set to address, "Free Parking," and reward, respectively.
   {
      super(address, "Free Parking");
      
      this.reward = reward;
   }

   // ****** MODIFIERS ******
   
   public void AddMoney(int money)
   // PRE:  money > 0 in Monopoly dollars
   // POST: money is added to reward
   {
      reward += money;
   }
   
   public void RemoveMoney()
   // POST: reward to set back to zero.
   {
      reward = 0;
   }
   
   // ****** ACCESSORS ******
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   // POST: FCTVAL == array of options player has upon landing on this FreeParkingSquare, to be used
   //       in a menu in a user interface.
   {
      if (reward == 0)                       // player does not collect any money
      {
         return new String[]{"Continue."};
      }
      else                                   // player collects money
      {
         return new String[]{"You landed on " + name + "! Collect a reward of $" + reward + "."};
      }
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of FreeParkingSquare
   {
      return super.toString() + " gives players $" + reward + " when landed on";
   }
}