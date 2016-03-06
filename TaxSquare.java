// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a tax square on the Monopoly board.

import java.lang.String;

public class TaxSquare extends BoardLocation
{
   private static final int 
            DEFAULT_TAX = 100;      // Default value of taxAmt in Monopoly dollars
   private int taxAmt;              // Amount Player must pay when landing on a TaxSquare, 
                                    //   domain > 0 in Monopoly dollars
   // ****** CONSTRUCTORS ******
   
   public TaxSquare()
   // POST: Creates an instance of TaxSquare where address is set to DEFAULT_ADDRESS, name is 
   //       set to a blank String, and taxAmt is set to DEFAULT_TAX.
   {
      this(DEFAULT_ADDRESS, " ", DEFAULT_TAX);
   }
   
   public TaxSquare(int address, String name, int tax)
   // PRE:  address >= 0
   //       name is initialized
   //       tax > 0 in Monopoly dollars
   // POST: Creates an instance of TaxSquare where data members addres, name, and taxAmt are set
   //       to address, name, and tax, respectively.
   {
      super(address, name);
      
      taxAmt = tax;
   }
   
   // ****** ACCESSORS ******
   
   public int GetTax()
   // POST: FCTVAL == Tax player must pay when landing on this square in Monopoly dollars
   {
      return taxAmt;
   }
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   // POST: FCTVAL == array of options player has upon landing on this TaxSquare, to be used in
   //       a menu in a user interface.
   {
      if (player.GetMoney() < taxAmt)        // player cannot afford tax
      {
         return  new String[]{"You can't pay the tax of $" + taxAmt 
                            + ". Declare bankruptcy."};
      }
      else                                   // player pays tax
      {
         return new String[]{"Pay tax of $" + taxAmt + "."};
      }
   }
   
   public String toString()
   // POST: FCTVAL == a String representing the current status of TaxSquare
   {
      return super.toString() + " costs $" + taxAmt + " to land on";
   }
}