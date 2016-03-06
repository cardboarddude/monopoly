// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a card square on the Monopoly board.

import java.lang.String;

public class CardSquare extends BoardLocation
{
   private static int max;          // Maximum amount of money player can win/lose when landing on a 
                                    //   card square, (domain: any integer in Monopoly dollars)
   private static int min;          // Minimum amount of money player can win/lose when landing on a 
                                    //   card square, (domain: any integer in Monopoly dollars)
   // ****** CONSTRUCTORS ******
   
   public CardSquare()
   // POST: Creates an instance of CardSquare with data members address and name set to 
   //       DEFAULT_ADDRESS and a blank String, respectively.
   {
      super(DEFAULT_ADDRESS, " ");
   }
   
   public CardSquare(int address, String name)
   // PRE:  address >= 0
   //       name is initialized
   // POST: Creates an instance of CardSquare with data members address and name set to 
   //       address and name.
   {
      super(address, name);
   }

   // ****** MODIFIERS ******
   
   public static void SetRange(int newMin, int newMax)
   // PRE:  min and max are initialized, Monopoly dollars (domain: any integer)
   // POST: min and max data members are set to min and max, respectively, for all instances of 
   //       CardSquare.
   {
      min = newMin;
      max = newMax;
   }

   // ****** ACCESSORS ******
   
   private int CalcMoney()
   // PRE:  min and max are both initialized s.t. min <= max in Monopoly dollars
   // POST: FCTVAL == The fees or earnings Player will lose or win in Monopoly dollars,
   //       respectively.
   {
      int range;           // Size of range of possible fees/earnings, range >= 0 in Monopoly $
      
      range = max - min;   // Range of possible fees/earnings
      
      return (int)(Math.random() * (range + 1) + min);
   }
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   // POST: FCTVAL == array of options player has upon landing on this CardSquare, to be used in a 
   //       menu in a user interface.
   {
      int money;              // Fees/earnings to be paid/collected by Player, money any integer
                              //   in Monopoly $
      money = CalcMoney();
      
      if (money > 0)          // Player collects money
      {
         return new String[]{"Collect $" + money + "."};
      }
      else if (money < 0)     // Player loses money
      {
         if (player.GetMoney() >= (-1 * money)) // Player has enough money to pay tax
         {
            return new String[]{"Pay tax of $" + (-1 * money) + "."};
         }
         else                                   // Player does not have enough money to pay tax
         {
            return  new String[]{"You can't pay the tax of $" + (-1 * money) 
                               + ". Declare bankruptcy."};
         }
      }
      else                    // Player neither loses nor collects money
      {
         return new String[]{"You didn't win/lose any money. Continue."};
      }
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of CardSquare
   {
      return super.toString() + " has a minimum win/loss of $" + min
           + " and maximum of $" + max;
   }
}