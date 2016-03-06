// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a railroad square on the Monopoly board.

import java.lang.String;

public class Railroad extends Property
{
   private static int[] rent;             // Cost of landing on a Railroad square, positive 
                                          //   number of Monopoly dollars. Cost depends on 
                                          //   number of Railroads owned.
   // ****** CONSTRUCTORS ******
   
   public Railroad()
   // POST: An instance of Railroad is created with address set to DEFAULT_ADDRESS, name set to 
   //       " ," and price set to DEFAULT_PRICE.
   {
      this(DEFAULT_ADDRESS, " ", DEFAULT_PRICE);
   }
   
   public Railroad(int address, String name, int price)
   // PRE:  address >= 0
   //       name is initialized
   //       price > 0 in Monopoly dollars
   // POST: An instance of Railroad is created with data members address, name, and price set 
   //       to address, name, and price.
   //       Note: rent is not initialized here because it is a static variable (same for all
   //             instances).
   {
      super(address, name, price);
   }

   // ****** MODIFIERS ******
   
   public static void SetRent(int[] newRent)
   // PRE:  newRent is initialized with as many elements as there are instances of Railroad with
   //       each element > 0 in Monopoly dollars
   // POST: Data member rent is set to newRent 
   {
      rent = newRent;
   }

   // ****** ACCESSORS ******
   
   @Override
   public int CalcRent(Dice dice)
   // PRE:  rent is initialized with as many elements as there are instances of Railroad
   //       owner is not null
   // POST: FCTVAL == The cost of landing on this Railroad in Monopoly dollars
   {  
      return rent[(this.GetOwner()).GetNumRd() - 1];
   }
   
   public String toString()
   // POST: A String representing the current state of this Utility
   {
      String rdRent;       // Representation of rent dependent on the number of railroads owned

      rdRent = "";
                                             // Iterates through the possible rent costs. An 
      for (int i = 0; i < rent.length; i++)  //   enhanced for loop is not used because we need
      {                                      //   to know the index of the current element.          
         rdRent += "\nWith " + (i + 1) + " railroad(s), the rent is $" + rent[i];
      }
      
      return super.toString() + " with a rent dependent\n" 
           + "on the number of railroads owned: " + rdRent;
   }
}