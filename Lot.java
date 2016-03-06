// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a lot (e.g. Marvin Gardens) square on the Monopoly board.

import java.lang.String;

public class Lot extends Property
{
   private static final 
      int DEFAULT_MAX_HOUSES = 4;   // Default value of maxHouses
   private static final 
      int DEFAULT_HOUSE_PRICE = 50; // Default value of housePrice in Monopoly dollars
   private static final             
      int[] DEFAULT_RENT = {50, 100
                          , 150, 200
                          , 250};   // Default value of rent in Monopoly dollars
   private static int maxHouses;    // Maximum number of houses on a single Lot, domain > 0

   private int[] rent;              // Cost of landing on a lot depending on the number of 
                                    //   houses/hotel, domain > 0 in Monopoly dollars. E.g. 1st 
                                    //   index  stores the rent cost one house.
   private int housePrice;          // Cost to buy 1 house (or 1 hotel if 4 houses already
                                    //   exist), domain > 0 in Monopoly dollars
   private String color;            // Color of the Lot (2 - 3 Lots may have the same color), 
                                    //   e.g. "Red"
   private int numHouses;           // Number of houses on this Lot
   private boolean hasHotel;        // true == 1 hotel is on Lot, false == no hotel is on Lot

   // ****** CONSTRUCTORS ******
   
   public Lot()
   // POST: An instance of Lot is created with address set to DEFAULT_ADDRESS, name set to " ,"
   //       price set to DEFAULT_PRICE, color set to " ," housePrice set to 
   //       DEFAULT_HOUSE_PRICE, and rent set to DEFAULT_RENT
   {
      this(DEFAULT_ADDRESS, " ", DEFAULT_PRICE, " ", DEFAULT_HOUSE_PRICE, DEFAULT_RENT);
   }
   
   public Lot(int address, String name, int price, String color, int housePrice, int[] rent)
   // PRE:  address >= 0
   //       color is initialized
   //       housePrice > 0 in Monopoly dollars
   //       rent[0] == rent cost without any buildings, middle elements are rent cost with index
   //         number of houses, and last element is the rent cost of one hotel (positive amount 
   //         in Monopoly $)
   // POST: An instance of Lot is created with data members address, name, price, color,
   //       housePrice, and rent set to address, name, price, color, housePrice, and rent,
   //       respectively. maxHouses is set to DEFAULT_MAX_HOUSES. numHouse is initialized to
   //       zero and hasHotel is set to false.
   {
      super(address, name, price);
      
      this.color = color;
      this.housePrice = housePrice;
      this.rent = rent;
      
      maxHouses = DEFAULT_MAX_HOUSES;
      numHouses = 0;
      hasHotel = false;
   }

   // ****** MODIFIERS ******
   
   public static void SetMaxHouses(int maxNumOfHouses)
   // PRE:	numOfHouses > 0
   // POST: Resets the maximum number of houses that can be placed on any given Lot. maxHouses
   //       is set to numOfHouses.
   {
      maxHouses = maxNumOfHouses;
   }
   
   public void AddHouse()
   // POST: A house is added to this Lot if the number of houses (numHouses)
   {
      numHouses++;               // Add another house
      
      if (numHouses > maxHouses) // Determines if a hotel should be created and houses removed
      {
         numHouses = 0;
         hasHotel = true;
      }
   }
   
   public void SellHouse()
   // PRE:  numHouses > 0
   // POST: One house is removed from this Lot and the owner of this Lot receive one-half of
   //       the purchase cost of a house.
   {
      numHouses--;
      owner.Receive(housePrice / 2);
   }
   
   public void SellHotel()
   // PRE:  hasHotel == true
   // POST: hasHotel is set to false (hotel is removed) and player recives the one-half of
   //       five houses.
   {
      hasHotel = false;
      owner.Receive((housePrice * 5) / 2);
   }
   
   // ****** ACCESSORS ******
   
   public boolean HasBuilding()
   // POST: FCTVAL == true, this Lot has either a hotel or at least one house
   {
      if (hasHotel || (numHouses > 0)) // If there is either hotel or one or more houses
         return true;
      return false;
   }
   
   public boolean HasHotel()
   // POST: FCTVAL == true, this Lot contains a hotel
   //       FCTVAL == false, otherwise
   {
      return hasHotel;
   }
   
   public int GetHousePrice()
   // POST: FCTVAL == Cost of purchasing a single house in Monopoly dollars.
   {
      return housePrice;
   }
   
   @Override
   public int CalcRent(Dice dice)
   // PRE:  rent is initialized with as many elements as there are instances of Lot
   //       owner is not null
   // POST: FCTVAL == The cost of landing on this Lot in Monopoly dollars.
   {    
      if (!hasHotel)                   // Has between 0 and maxHouses number of houses
      {
         return rent[numHouses];
      }
      else                             // Has a single hotel on this Lot
      {
         return rent[maxHouses + 1];    
      }
   }
   
   public String toString()
   // POST: A String representing the current state of this Lot
   {
      String buildingStatus;        // String representation of how many houses/hotel player has
      
      if (hasHotel)                 // There is a hotel on this Lot
      {
         buildingStatus = "hotel";
      }
      else                          // There are possibly houses but no hotel on this Lot
      {
         buildingStatus = numHouses + " houses";
         if (numHouses == 1)                       // There is a single house on this Lot
         {
            buildingStatus                                                 // Removes trailing
               = buildingStatus.substring(0, buildingStatus.length() - 1); //   's' of 
         }                                                                 //   buildingStatus
      }
   
      return super.toString()
             + "\nCurrent Status of this '" + color + "' Lot:"
             + "\n > Has " + buildingStatus
             + "\n > Costs $" + CalcRent(new Dice(1)) + " to land on"
             + "\n > Costs $" + housePrice + " to build a house";
   }
}