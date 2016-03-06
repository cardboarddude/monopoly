// Name:        Elliot Sterk and Kristopher Wagner
// Section:     2
// Date:        2/12/2012
// Program:     Project 1, Monopoly
// Description: This class models any number of dice and their corresponding values.

import java.lang.String;

public class Dice
{
   private static final int 
         DEFAULT_NUM_DICE = 2;   // Default value for numDice
   
   private int[] dice;           // Values rolled by each die, 1 <= dice[i] <= 6
   private int numDice;          // Total number of dice (size of dice array), numDice > 0
   // ****** CONSTRUCTORS ******
   
   public Dice()
   // POST: An instance of Dice is created with data member numDice set to DEFAULT_NUM_DICE.
   {
      this(DEFAULT_NUM_DICE);
   }
   
   public Dice(int numDice)
   // PRE:  numDice > 0
   // POST: An instance of Dice is created with data member numDice set to numDice and dice is
   //       allocated memory to hold numDice number of elements.
   {
      this.numDice = numDice;
      dice = new int[numDice];
   }
   
   // ****** MODIFIERS ******
   
   public int Roll()
   // POST: FCTVAL == Sum of all values stored in dice
   {
      int totalRoll;                   // Total roll of all dice, totalRoll >= numDice
      
      totalRoll = 0;                   // Initializes total
      
      for (int i = 0; i < dice.length; i++)        // Each element of dice is accessed and
      {                                            //   modified
         dice[i] = (int)(Math.random() * 6 + 1);   // Creates a random integer in the 
         totalRoll += dice[i];                     //   domain: [1, 6]
      }
      
      return totalRoll;
   }
   
   // ****** ACCESSORS ******
   
   public boolean RolledDoubles()
   // PRE:  dice is initialized with at least 2 elements, 1 <= dice[i] <= 6
   // POST: FCTVAL == true, all the values in dice are the same
   //       FCTVAL == false, otherwise
   {
      
      for (int i = 1; i < dice.length; ++i)  // Access every element in dice and allows for
      {                                      //   current index to be used
         if (dice[0] != dice[i])             // Tests if all the values in dice are the same
         {
            return false;                    // Doubles were not rolled
         }
      }
      
      return true;                           // Doubles were rolled
   }
   
   public int GetRoll()
   // PRE:  dice is initialized (1 <= dice[i] <= 6)
   // POST: FCTVAL == Sum of all values stored in dice
   {
      int totalRoll;                   // Total roll of all dice, totalRoll >= numDice
      
      totalRoll = 0;                   // Initializes total
      
      for (int cur : dice)             // Each element of dice is accessed (index is irrelevant)
      {
         totalRoll += cur;
      }
      
      return totalRoll;
   }
   
   public String toString()
   // POST: FCTVAL == String representation of this Dice
   {
      String values;          // Stores the values of each die
      
      values = "";
      for (int cur : dice)    // Access each element in dice (don't need index)
      {
         values += "Die: " + cur + "\n";
      }
      
      return "Dice has these rolled values:\n" + values;
   }
}