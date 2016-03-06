// Name:        Elliot Sterk and Kris Wagner
// Section:     2
// Date:        2/5/2012
// Program:     Project 1, Monopoly
// Description: This class models a player in the game of Monopoly.

public class Player
{
    private static final int 
            DEFAULT_MONEY  = 250;      // Default amount of money given to Player, Monopoly $
    private static final int 
            MAX_PROPS = 28;            // Maximum number of Property instances Player can own
            
    private int money;                 // Amount of money held by Player in Monopoly $, 
                                       //   money >= 0
    private String token;              // Name of player's game piece, e.g. "Thimble"
    private BoardLocation location;    // Location of token on game board
    private Property[] propsOwned;     // Properties purchased by Player
    private int numProps;              // Logical size of propsOwned
    private int numRd;                 // Number of Railroad properties owned, numRd >= 0
    private int numUtils;              // Number of Utility properties owned, numUtils >= 0
    private int turnsInJail;           // Number of turns player has been in jail for, 
                                       //   turnsInJail >= 0
    private int doublesRolled;         // Number of consecutive dice rolls that resulted in 
                                       //   all the dice showing the same value, 
                                       //   0 <= doublesRolled <= 3
    // ****** CONSTRUCTORS ******
    
    public Player()
    // POST: An instance of Player is created with data member money set to DEFAULT_MONEY and
    //       location set to null.
    {
        this(DEFAULT_MONEY, null);
    }
    
    public Player(int money, BoardLocation startPos)
    // PRE:  startPos is initialized
    //       money >= 0 in Monopoly $
    // POST: An instance of Player is created with data member money set to money, token set to
    //       a blank String, location set to startPos, propsOwned is dynamically allocated 
    //       MAX_PROPS memory locations, and numRd, numUtils, and turnsInJail are set to zero.
    {
        this.money = money;
        token = " ";
        location = startPos;
        propsOwned = new Property[MAX_PROPS];
        numRd = 0;
        numUtils = 0;
        numProps = 0;
        turnsInJail = 0;
        doublesRolled = 0;
    }
    
    // ****** MODIFIERS ******
    
    public void AddDoublesRolled()
    // POST: this player rolled a double so doublesRolled is incremented by one.
    {
        doublesRolled++;
    }
    
    public void ResetDoublesRolled()
    // POST: this player rolled a non-double dice roll so doublesRolled is set to zero.
    {
        doublesRolled = 0;
    }
    
    public void LeaveJail()
    // POST: this player leaves jail so turnsInJail is set to zero.
    {
        turnsInJail = 0;
    }
    
    public void AddTurnInJail()
    // PRE:  turnsInJail is initialized
    // POST: Increments the number of turns in jail (turnsInJail) by one.
    {
        turnsInJail++;
    }
    
    public void BuildHouse(Lot lot)
    // PRE:  lot is in propsOwned
    //       Player has enough money to buy a house
    // POST: a house is added to lot and the cost of a house for lot is removed from Player's
    //       money
    {
       if (money > lot.GetHousePrice())          // Verifies that Player has enough money to 
       {                                         //   purchase a house.
           lot.AddHouse();
           money -= lot.GetHousePrice();
       }
    }
    
    public void BuyProperty()
    // PRE:  This Player's current location is on a purchasable property.
    // POST: The price of the property at the current location is subtracted from this.money
    //          and the current location is added to propsOwned
    {
       money -= ((Property)location).GetPrice();   // Paying for the property
                                                   // Since location is a BoardLocation it does not
                                                   //    have a GetPrice() method. This method
                                                   //    will only be called when this Player is
                                                   //    on a Property that can be purchased by
                                                   //    them. That is why location is casted
                                                   //    as a Property.
       propsOwned[numProps] = ((Property)location);// Adding the property to the list of ones owned
       ((Property)location).SetOwner(this);        // Stores this Player as the owner
       
       if (propsOwned[numProps] instanceof Railroad)        // Newly added property is a Railroad
       {
         numRd++;                                           // Increment Railroad counter
       }
       else if (propsOwned[numProps] instanceof Utility)    // Newly added property is a Utility
       {
         numUtils++;                                        // Increment Utility counter
       }
       
       numProps++;                                 // Increment Property counter    
    }
    
     public void Receive(int amount)         // For receiving rent or winnings
    // PRE:  amount > 0 in Monopoly dollars
    // POST: amount is added to this.money
    {
       this.money += amount;
    }
    
     public boolean Pay(int amount)          // Remove money from Player
    // PRE:  amount > 0 in Monopoly dollars
    // POST: amount is subtracted from this.money
    //          FCTVL == true if the player's money is still > 0
    //          FCTVL == false if the player's money <= 0 (Player is Bankrupt)
    {
       this.money -= amount;                 // Removing amount from player's money
       
       return (money > 0);
    }
    
    public void SetToken(String token)
    // PRE:  token is initialized to the name of a Monopoly piece
    // POST: this Player's token is set to token
    {
       this.token = token;
    }
    
    public void SetLocation(BoardLocation location)
    // PRE:  location is initialized to a valid location on the Monopoly board
    // POST: this Player's location is set to location
    {
       this.location = location;
    }
    
    // ****** ACCESSORS ******
    
    public int GetDoublesRolled()
    // POST: FCTVAL == Number of doubles this player has rolled
    {
        return doublesRolled;
    } 
   
    public String[] GetBuildLots(boolean toSell)
    // PRE:  toSell, true == determine what lots have buildings to be sold
    //               false == determine what lots can be built on
    // POST: FCTVAL == A set of Lots owned by this Player that have buildings to be sold or 
    //       space to be built on (a house or hotel can be afforded and built on Lot).
    {
       String[] lots;               // Set of all lots player can build on with possible null
                                    //   elements where null indicates lot does not have 
                                    //   buildings to sell or space to build buildings, 
                                    //   e.g. {"Marvin Gardens", "Park Place", null}
       String [] returnLots;        // Set of all lots player can build on (without null 
                                    //   elements), e.g. {"Marvin Gardens", "Park Place"}
       int index;                   // Current index of lots, index >= 0
                                    
       index = 0;
       lots = new String[numProps - numRd - numUtils];   // Allocated enough memory to store all
                                                         //   lots owned by this Player
       for (Property curProp : propsOwned)
       {
           if (curProp instanceof Lot)          // This Property is a lot that can contain
           {                                    //   houses and hotels.
               if (toSell)                      // If player wants to sell buildings
               {
                   if (((Lot)curProp).HasBuilding())              // If curent lot has a 
                   {                                              //   building
                       lots[index++] = ((Lot)curProp).GetName();  // Lot added to sellable list
                   }
               }
               else                             // If player wants to buy buildings
               {                                                 // The current player can 
                   if ((((Lot)curProp).GetHousePrice() <= money) //   afford to buy a single 
                        && (!((Lot)curProp).HasHotel()))         //   house and the Lot does not
                   {                                             //   have a hotel.
                       lots[index++] = ((Lot)curProp).GetName(); // Lot added to buyable list
                   }
               }
           }
       }
       
       index = 0;                                     // First index of lots
       
       while ((index++ < lots.length) 
           && (lots[index - 1] != null));             // Determines the first location of a null
           
       index--;                                       // Remove last index increment
       
       if (index == 0)                                // If player can't build anywhere
       {
           return null;                               // No lots can be built-on/sold-from
       }
       
       returnLots = new String[index];
                                                         
       for (int i = 0; i < returnLots.length; i++)     // Creates return array of lots player
       {                                               //   without null elements.
           returnLots[i] = lots[i];
       }
       
       return returnLots;
    }
    
    public int GetTurnsInJail()
    // PRE:  turnsInJail is initialized
    // POST: FCTVAL == the number of turns that this player has been in jail for (turnsInJail)
    {
      return turnsInJail;
    }
   
    public String GetProperties()
    // POST: FCTVAL == Representation of all the properties that player owns separated by commas
    {
        String props;       // Comma-separated representation of properties owned by this player
        
        props = "";
        
        for (int i = 0; i < numProps; i++)      // Iterated through the logical size of 
        {                                       //   propsOwned.
            props += propsOwned[i].GetName() + ", ";
        }
        
        if (props == "")      // props does not need to have any characters removed
        {
            return props;
        }
        else                  // props contains an extra space and comma at end of String
        {
            return props.substring(0, props.length() - 2);     // Removes last space and comma
        }
    }
    
    public BoardLocation GetLocation()
    // POST: FCTVL == Current location of player
    {
       return this.location;
    }
    
    public int GetNumRd()
    // POST: FCTVAL == Number of Railroad properties owned by this Player
    {
       return numRd;
    }

    public int GetNumUtils()
    // POST: FCTVAL == Number of Utility properties owned by this Player
    {
       return numUtils;
    }
    
    public int GetMoney()
    // POST: FCTVAL == Amount of money held by this Player
    {
       return money;
    }
    
    public String GetToken()
    // POST: FCTVAL == Which token game piece player is using
    {
       return token;
    }
    
    public String toString()
    // POST: A String representing the current state of this Player
    {
        String properties;          // Properties player owns by title of each property. 
                                    //   (Separated by commas).  
        String jailStatus;          // Jail status for this player, 
                                    //   e.g. "Has been in jail for 2 turns"
        properties = GetProperties();
        
        jailStatus = "";
        
        if (turnsInJail != 0)
        {
            jailStatus = "Has been in jail for "
                       + turnsInJail + " turns";
        }
        
        return "The Player as " + token
             + " is located at " + location.GetName()
             + " has $" + money
             + " and owns:\n" + properties
             + "\nNumber of Properties: " + numProps
             + "\nNumber of Railroads: " + numRd
             + "\nNumber of Utilities: " + numUtils
             + "\nConsecutive doubles rolled: " + doublesRolled
             + "\n" + jailStatus;
    }
}