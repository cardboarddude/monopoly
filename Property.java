// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a property square on the Monopoly board.

import java.lang.String;

public abstract class Property extends BoardLocation
{
   protected static final 
      int DEFAULT_PRICE = 100;      // Default value of price in Monopoly dollars
   
   protected int price;             // Cost to purcahse the Property in Monopoly $, domain > 0
   protected Player owner;          // Owner of this Property

   // ****** CONSTRUCTORS ******
   
   public Property()
   // POST: An instance of Property is created with data members address, name, and price set 
   //       to DEFAULT_ADDRESS, a blank String, and DEFAULT_PRICE, respectively.
   {
      this(DEFAULT_ADDRESS, " ", DEFAULT_PRICE);
   }
   
   public Property(int address, String name, int price)
   // PRE:  address >= 0
   //       price > 0 in Monopoly dollars 
   //       owner is initialized
   // POST: An instance of Property is created with data members address, name, and price set to
   //       address, name, and price. owner is set to a null pointer.
   {
      super(address, name);
      this.price = price;
      owner = null;
   }

   // ****** MODIFIERS ******
   
   public void SetOwner(Player player)
   // PRE:  player is initialized
   // POST: owner will be set to player
   {
      owner = player;
   } 
   
   // ****** ACCESSORS ******
   
   public int GetPrice()
   // POST: FCTVAL == The price of this property
   {
      return price;
   }
   
   public Player GetOwner()
   // POST: FCTVAL == The Player who owns this Property. A pointer to a Player object is 
   //       returned
   {
      return owner;
   } 
   
   public abstract int CalcRent(Dice dice);
   // PRE:  subclass instance of Property is initialized
   //       dice is initialized
   // POST: FCTVAL == The cost of landing on this Property in Monopoly dollars.
   
   @Override
   String[] getPossibleActions(Player player, Dice dice)
   // PRE:  player is initialized
   //       dice is initialized
   // POST: FCTVAL == array of options player has upon landing on this Property, to be used in 
   //       a menu in a user interface.
   {
      if (owner == player)                                        // 1) player owns this 
      {                                                           //    Property
         return new String[]{"Continue. You own " + name + "."};  // player has no possible 
      }                                                           //   actions
      else if (owner == null)                                     // 2) The bank owns this 
      {                                                           //    Property
         if (player.GetMoney() > price)                           // player can afford property
         {
            return new String[]{"Buy " + name + " for $" + price  // Allow player to buy 
                              + "."                               //   Property or continue.
                              , "Continue."};
         }                                         
         else                                                     // player can't afford
         {                                                        //   property
            return new String[]{"Continue."};                     // Allow player to 
         }                                                        //   continue.
      }
      else                                                        // 3) Another player owns this
      {                                                           //    Property
         if (player.GetMoney() >= CalcRent(dice))                 // player can afford to pay
         {                                                        //   owner, 1 is abitrary
            return  new String[]{"Pay rent $" 
                               + CalcRent(dice) + "."};           // Call subclass CalcRent()           
         }                                                        //   method
         else                                                     // player is bankrupt
         {
            return  new String[]{"You can't pay the rent. Declare"
                               + " bankruptcy."};
         }
      }
   }
   
   public String toString()
   // POST: A String representing the current state of this Property
   {
      String state;           // State of this Property
      
      state = super.toString() + " costs $" 
            + price;
      if (owner == null)                        // There is no owner
      {
         return  state + " and is unowned";
      }
      else                                      // There is an owner
      {
         return state + " and is owned by " 
              + owner.GetToken();
      }
   }
}