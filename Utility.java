// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a utility square on the Monopoly board.

import java.lang.String;

public class Utility extends Property
{
   private static int[] rentFactor;       // Rent multiplier > 0 (multiply with the dice rolls 
                                          //   to get rent.
   // ****** CONSTRUCTORS ******
   
   public Utility()
   // POST: An instance of Utility is created with address set to DEFAULT_ADDRESS, name set to 
   //      " ," and price set to DEFAULT_PRICE.
   //       Note: rent is not initialized here because it is a static variable (same for all
   //             instances).
   {
      this(DEFAULT_ADDRESS, " ", DEFAULT_PRICE);
   }
   
   public Utility(int address, String name, int price)
   // PRE:  address >= 0
   //       name is initialized
   //       price > 0 in Monopoly dollars
   // POST: An instance of Utility is created with data members address, name, and price set to
   //       address, name, and price.
   {
      super(address, name, price);
   }

   // ****** MODIFIERS ******
   
   public static void SetFactor(int[] newFactor)
   // PRE:  newFactor is initialized with as many elements as there are instances of Utility
   // POST: rentFactor for all instances of Utility is set to the newFactor parameter
   {
      rentFactor = newFactor;
   }

   // ****** ACCESSORS ******
   
   @Override
   public int CalcRent(Dice dice)
   // PRE:  rentFactor is initialized with as many elements as there are instances of Utility
   //       owner is not null
   //       dice is initialized
   // POST: FCTVAL == The cost of landing on this Utility in Monopoly dollars. 
   {
      int index;                                // rentFactor index, index >= 0
      
      index = (this.GetOwner()).GetNumUtils() - 1;
      
      return rentFactor[index] * dice.GetRoll();
   }
   
   public String toString()
   // POST: A String representing the current state of this Utility
   {
      String rentFact;              // Representation of rentFactor
      
      rentFact = "";
      for (int cur : rentFactor)
      {
         rentFact += cur + ", ";
      }
      
      return super.toString() + "\nwith rent factors of " 
           + rentFact.substring(0, rentFact.length() - 3);
   }
}