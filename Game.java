// Name:        Kristopher Wagner and Elliot Sterk
// Section:     2
// Date:        2/12/2012
// Program:     Project 1, Monopoly
// Description: This class models a monopoly game.

import java.lang.String;
import java.util.Scanner;
import java.io.*;
import  javax.swing.*;

public class Game
{
   private static final int            // Default amount of money each player will receive
      DEFAULT_MONEY = 1500;            //   in Monopoly dollars
   private static final int            // Maximum number of sequential double number dice rolls
      MAX_DOUBLES = 3;
   private BoardLocation[] spaces;     // Squares on the Monopoly board in sequential order,
                                       //   number of elements > 1
   private Player[] players;           // Players playing the Monopoly game, 
                                       //   0 < number of elements <= 8
   private int curPlayer;              // Current player whose turn it is,
                                       //   0 < curPlayer <= players.length
   private Dice dice;                  // Representation of one or more physical dice
   // ****** CONSTRUCTORS ******
   
   public Game()
   // POST: An instance of game is created with players initialized with 2 players starting with
   //       DEFAULT_MONEY Monopoly dollars each.
   {
      this(2, DEFAULT_MONEY);    
   }
   
   public Game(int numPlayers, int startingMoney)
   // PRE:  0 < numPlayers <= 8
   //       0 < startingMoney in Monopoly dollars
   // POST: An instance of game is created with players initialized with numPlayers players
   //       starting with DEFAULT_MONEY Monopoly dollars each. spaces is initialized with 40 
   //       Monopoly board squares, dice is set to represent 2 physical dice, and the starting
   //       player is randomly determined and stored in curPlayer.
   {
      Scanner input;                         // Input by user from console to get the players'
                                             //   tokens.
      players = new Player[numPlayers];      // numPlayers Players are created
      spaces = new BoardLocation[40];        // A board with 40 squares is initialized
      dice = new Dice();                     // dice defaults to representing two physical dice
      input = new Scanner(System.in);
      
      LoadBoardLocations();                  // Reads text files to initialize all the board
                                             //   squares    
      // PURPOSE: To initialize all the players
      // REASON:  This loop will access all the players (determinate) but a counter is needed,
      //          therefore a normal for loop is preferred over an enhanced for loop.
      for (int i = 0; i < numPlayers; i++)
      {
         players[i] = new Player(startingMoney  // Creates a new player with starting money of
                               , spaces[0]);    //   startingMoney Monopoly $ with a starting
                                                //   location at Go
         System.out.print("Player: " + (i + 1) + ": "
                        + "Please enter which token you'd like to be.\n"
                        + "Note: No two players can have the same token: ");
         players[i].SetToken(input.nextLine());
         System.out.println("");                      // Outputs a blank line to console
      }
      
      curPlayer = (int)(Math.random() * numPlayers + 1);     // Randomly decides which player 
   }                                                        //   goes first
   
   // ****** MODIFIERS ******
   
   public void RunDemoMode()
   // POST: First each player gets (28/numPlayers) properties and starting money amount of 1500
   //       Monopoly dollars and then the game runs as usual.
   {
      // PURPOSE: To distribute all the properties to the players to speed up the game.
      // REASON:  All of the BoardLocations will be accessed (determinate) and the index is not
      //          required in the body of the loop, so an enhanced for loop is used.
      for(BoardLocation cur : spaces)        // players receive properties for free
      {
         if (cur instanceof Property)        // Checks if the current square is buyable
         {
            players[curPlayer - 1].SetLocation(cur);        // Moves player to next available
                                                            //   property location.
            players[curPlayer - 1].BuyProperty();           // Player buys property are current 
                                                            //   location.
            players[curPlayer - 1].Receive(((Property)cur)  // Player is refunded for the money
                                  .GetPrice());             //   spent on the property.
            curPlayer++;                                    // Next player
            if(curPlayer > players.length)   // If the current player (curPlayer) is the last
               curPlayer = 1;                //   player, set curPlayer to the first player
         }
      }
      
      for (Player cur : players)             // Access each element of players but index is not
      {                                      //   needed.
         cur.SetLocation(spaces[0]);         // Return players back to the starting Go square
      }
      
      Run();               // Game begins
   }
   
   private void LoadBoardLocations()
   // PRE:  Files "Name.txt," "Price.txt," "Rent.txt," "Color.txt," and "HousePrice.txt" have
   //       been created.
   // POST: spaces is filled with all of the locations on the board.
   {
      Scanner nameScn;                 // Scanner to load the name from file
      Scanner priceScn;                // Scanner to load the price from file
      Scanner rentScn;                 // Scanner to load the rent from file
      Scanner colorScn;                // Scanner to load the color from file
      Scanner housePriceScn;           // Scanner to load the house price from file
                                       // NOTE: All data in files are in sequential order
      File nameFile;                   // File that contains the names of board locations 
      File priceFile;                  // File that contains the prices of properties 
      File rentFile;                   // File that contains the rent of properties 
      File colorFile;                  // File that contains the color of lots 
      File housePriceFile;             // File that contains the house prices for lots
          
      String name;                     // Temporary storage for a name of a BoardLocation,
                                       //   e.g. "L.Mediterranean Avenue" where 'L' indicates
                                       //   this BoardLocation is a Lot
      String price;                    // Temporary storage for a price of a Property, e.g.
                                       //   "100"
      String color;                    // Temporary storage for a color of a Property, e.g.
                                       //   "Light Purple"
      int housePrice;                  // Temporary storage for a house price, housePrice > 0
                                       //   in Monopoly $
      int[] rentArray;                 // Temporary storage for the rent, rentArray[i] > 0
                                       //   in Monopoly $
      int[] rdRent;                    // Railroad rents, rdRent[i] > 0 in Monopoly $
      int[] rentFactor;                // Utility rent factors, rentFactor[i] > 0
      String[] rentStrings;            // Temporary storage for parsed rent
      String rent;                     // Temporary storage for preParsing of rent
      int taxCounter;                  // Index of taxAmt[], e.g. 0 = Income tax, 1 = Luxury tax
      int[] taxAmt;                    // Cost of landing on a TaxSquare in Monopoly dollars
                                       //   taxAmt[i] > 0
      try                              // Throws FileNotFoundException when a file is not found
      {
         nameFile = new File("Name.txt");
         priceFile = new File("Price.txt");
         rentFile = new File("Rent.txt");
         colorFile = new File("Color.txt");
         housePriceFile = new File("HousePrice.txt");
      
         nameScn = new Scanner(nameFile);
         priceScn = new Scanner(priceFile);
         rentScn = new Scanner(rentFile);
         colorScn = new Scanner(colorFile);
         housePriceScn = new Scanner(housePriceFile);
      
         rdRent = new int[]{25, 50, 100, 200};  // All of the railroads have the same rent
         rentFactor = new int[]{4, 10};         // Both utilites have the same rent conversion
         taxAmt = new int[]{200, 75};           // Taxes in Monopoly dollars for 2 TaxSquares
         taxCounter = 0;                        // taxAmt index is set to the first element
         
         // PURPOSE: To read from files all of the information for the board squares and store 
         //          in spaces.
         // REASON:  This loop will iterate 40 times (determinate) and the index is needed so a
         //          for loop is requried.
         for (int i = 0; i < 40; i++)
         {
           name = nameScn.nextLine();     // Stores the next line from console in name
           rentArray = new int[6];
           
           switch(name.charAt(0))
           {
              case 'L':                         // Current BoardLocation is a Lot
                 price = priceScn.nextLine();
                 color = colorScn.nextLine();
                 housePrice = Integer.parseInt
                             (housePriceScn.nextLine());
                 rent = rentScn.nextLine();              // Read rents from file, 
                                                         //   e.g. "0 5 15 25 50 75"
                 rentStrings = rent.split(" ");          // Splits rents into individual Strings
                                                         //   wherever there is a space.
                 // PURPOSE: To parse the integers from rentStrings are store in rentArray.
                 // REASON:  This loop will access all of the values in rentStrings and rentArrays
                 //          but a counter variable is needed to copy correctly. Therefore, a 
                 //          for loop is required.
                 for (int c = 0; c < rentStrings.length; c++)
                 {
                    rentArray[c] = Integer.parseInt(rentStrings[c]);
                 }
                 
                 spaces[i] = new Lot(i, name.substring(2)
                           , Integer.parseInt(price), color 
                           , housePrice, rentArray);
                 break;
              
              case 'R':                         // Current BoardLocation is a Railroad
                 price = priceScn.nextLine();
                 spaces[i] = new Railroad(i, name.substring(2), Integer.parseInt(price));
                 ((Railroad)spaces[i]).SetRent(rdRent);
                 break;
                 
              case 'U':                         // Current BoardLocation is a Utility
                 price = priceScn.nextLine();
                 spaces[i] = new Utility(i, name.substring(2), Integer.parseInt(price));
                 ((Utility)spaces[i]).SetFactor(rentFactor);
                 break;
              
              case 'T':                         // Current BoardLocation is a TaxSquare
                 spaces[i] = new TaxSquare(i, name.substring(2)
                                         , taxAmt[taxCounter]);
                 taxCounter++;                 
                 break; 
                 
              case 'C':                         // Current BoardLocation is a CardSquare
                 spaces[i] = new CardSquare(i, name.substring(2));
                 ((CardSquare)spaces[i]).SetRange(-200, 200);
                 break;
              
              case 'G':                         // Current BoardLocation is a GoSquare
                 spaces[i] = new GoSquare(i, 200);    // Sets reward for GoSquare to 200 
                 break;                               //   Monopoly $
              
              case 'J':                         // Current BoardLocation is a JailSquare
                 spaces[i] = new JailSquare(i, 3, 50);// Sets turns in jail to 3 and bail to 50
                 break;                               //   Monopoly $
           
              case 'Z':                         // Current BoardLocation is a GoToJailSquare
                 spaces[i] = new GoToJailSquare(i);
                 break;
              
              case 'F':                         // Current BoardLocation is a FreeParkingSquare
                 spaces[i] = new FreeParkingSquare(i);
            }
         }
      }
      catch (FileNotFoundException fileEx)
      {
         System.out.println("Attempted to open a file,"
                          + " but file could not be found in current directory.");
      }
   }
   
   private boolean Roll()
   // POST: FCTVAL == true, a player has lost and thus the game is over
   //       FCTVAL == false, the game is not over
   //       A player will roll dice (dice.Roll()) and will move to a new location on the board,
   //       remain in jail, or pay bail to get out of jail. Then player will select an action
   //       from a menu cooresponding to the BoardLocation landed on and that action will be 
   //       excecuted.
   {
      String[] possibleActions;           // Actions the player can do at their new location
	   int actionChoice;                   // Index of possibleActions selected by the user, 
                                          //   0 <= actionChoice < possibleActions.length()
      ActionsMenu menu;                   // Menu to be displayed to user
      
      menu = new ActionsMenu();
      dice.Roll();
      
      if (dice.RolledDoubles())           // Determines if player rolled doubles
      {
         System.out.println("You rolled doubles!");
         players[curPlayer - 1].AddDoublesRolled();   // Tally of player's dice double rolling
      }                                               //   is incremented.
      
      if (players[curPlayer - 1].GetDoublesRolled() >= MAX_DOUBLES) // Checks if player rolled
      {                                                             //   too many doubles
         return false;                                              // Player rolled the maximum
      }                                                             //   number of doubles and
                                                                    //   lost turn
      System.out.println("Your roll total is: " + dice.GetRoll()+ "\n");
      
      if (players[curPlayer - 1].GetTurnsInJail() == 0)  // Player is not in jail
      {
         MovePlayer();                                   // Update player's location
      }
      else if ((players[curPlayer - 1].GetTurnsInJail() > 0)   // Player is in jail but rolled
             && (dice.RolledDoubles()))                        //   doubles.
      {
         players[curPlayer - 1].LeaveJail();             // Player leaves jail
         players[curPlayer - 1].ResetDoublesRolled();    // Player double tally is set to 0
         MovePlayer();                                   // Player moves out of jail with rolled
      }                                                  //   doubles.
      
      System.out.println("You landed on " 
                 + (players[curPlayer - 1].GetLocation()).GetName());
      
      possibleActions = (players[curPlayer - 1].GetLocation())
                        .getPossibleActions(players[curPlayer - 1], dice);
      actionChoice = menu.runActionsMenu(possibleActions);
      return ParseAction(possibleActions[actionChoice]);    // Player takes action and if player
   }                                                        //   didn't loose, game continues
   
   public void Run()
   // POST: The game of monopoly is played out turn by turn until someone looses or quits.
   {
      boolean endGame;     // Flag for determining if the game ended,
                           //   false == game has ended
                           //   true == game is still going
      endGame = false;     // The game is starting so we do not want to end the game
      
      // PURPOSE: To run the monopoly game turn by turn
      do
      {
         System.out.println("\nIt is " 
             + players[curPlayer - 1].GetToken() 
             + "'s turn.\n");
         endGame = Turn();               // Current player's (player[curPlayer - 1]) turn occurs
         curPlayer++;                    // Next players turn
         if (curPlayer > players.length) // If curPlayer has exceeded its bounds
         {
            curPlayer = 1;                // Reset curPlayer to the first players
         }
      }
      while(!endGame);                 // While the game has not ended
      
      PopUpAllBoardInfo();             // Display the final game state
   }
   
   private void MovePlayer()
   // POST: The current player is moved by the value of their dice roll around the board
   //       and if the player passed a GoSquare they receive a reward
   {
      int startIndex;               // Starting index of player in spaces, 
                                    //   0 <= startIndex < spaces.length
      int endIndex;                 // Final index of player in spaces BoardLocations, 
                                    //   0 <= endIndex < spaces.length
      startIndex = (players[curPlayer - 1].GetLocation()).GetAddress();
      
      if ((startIndex + dice.GetRoll()) < spaces.length)         // Player has not passed Go
      {
         endIndex = startIndex + dice.GetRoll();
      }
      else                                                       // Player has passed Go and 
      {                                                          //   looped around the board
         endIndex = startIndex + dice.GetRoll() - spaces.length;
         players[curPlayer - 1].Receive(((GoSquare)spaces[0])
                                         .GetReward());          // Player gets money for 
      }                                                          //   passing go
      
      players[curPlayer - 1].SetLocation(spaces[endIndex]);      // Player location is changed
   }
   
   private boolean Turn()
   // POST: FCTVAL == true, the game has ended
   //       FCTVAL == false, the game is still going
   //       A player (players[curPlayer - 1]) takes a turn in Monopoly. This player will
   //       continue the turn until player looses (runs out of money), rolls (no doubles), or
   //       rolls with doubles up to 3 times.
   {
      boolean isTurnOver;     // State of player's turn, true == turn's over, false == otherwise
      String[] actions;       // Possible actions player can take after landing on a square,
                              //   e.g. {"Buy Park Place", "Continue"}
      String[] standardMenu;  // Actions player can take before rolling, e.g. {"View board"}
	   int menuChoice;         // Index of standardMenu selected by the user, 
                              //   0 <= menuChoice < standardMenu.length()
      ActionsMenu menu;       // Menu that displays a menu to console and allow user to make a 
                              //   selection
      isTurnOver = false;
      standardMenu = new String[]{"Manage houses/hotels"
                                , "Roll", "View board"
                                , "Quit game"};
      menu = new ActionsMenu();
      
      do
      {
         menuChoice = menu.runActionsMenu(standardMenu); // Display menu and get menu selection
         
         switch (menuChoice)        // Selects appropriate action based on user selection
         {
            case 0:                 // Manage houses and hotels
               ManageBuildings();
               break;
            
            case 1:                 // Roll
               if (Roll())                // Player lost
               {
                  return true;            // Game is over
               }
               if (!dice.RolledDoubles()) // Player didn't roll doubles
               {
                  isTurnOver = true;      // Turn is over
               }
               break;
               
            case 2:                 // View board
               PopUpAllBoardInfo();
               break;
            
            case 3:                 // Quit game
               return true;
         }
                                                                       // Determines if the 
         if (players[curPlayer - 1].GetDoublesRolled() >= MAX_DOUBLES) //   maximum number of 
         {                                                             //   doubles were rolled
            players[curPlayer - 1].SetLocation(spaces[10]);            // Player is sent to jail
            System.out.println("Sorry but you rolled "
                             + players[curPlayer - 1].GetDoublesRolled()
                             + " doubles in a row and were sent to jail."
                             + " Your turn is over.");
            players[curPlayer - 1].AddTurnInJail();               // First turn in jail
            isTurnOver = true;                                    // Turn is over
         }
         
         if (players[curPlayer - 1].GetTurnsInJail() > 0)   // Is Player in jail?
         {
            isTurnOver = true;                              // Player is still in jail so turn 
         }                                                  //   is over.
      } while (!isTurnOver);                       // While this player's turn is still active
      
      players[curPlayer - 1].ResetDoublesRolled(); // Player now has rolled zero 
                                                   //   doubles for next turn.
      return false;                                // The game is not over
   }
   
   public void ManageBuildings()
   // POST: Modifies lots by allowing the user to buy or sell houses and hotels. Houses
   //       and hotels are sold for half their retail value.
   {
	   int choice;                // Index of lots selected by the user, 
                                    //   0 <= actionChoice < lots.length()
      ActionsMenu menu;             // Menu to be displayed to user
      String[] options;
      
      if (players[curPlayer - 1].GetBuildLots(true) == null)
      {
         options = new String[]{"Buy building"};
      }
      else 
      {
         options = new String[]{"Buy building", "Sell building"};
      }
      
      menu = new ActionsMenu();
      
      if (players[curPlayer - 1].GetProperties() != "")       // If player has lots
      {
         System.out.println("\nBuy/Sell Buildings:");
         choice = menu.runActionsMenu(options);
         BuySellBuildings(choice);
      }
      else                    // Player doesn't have any lots
      {
         System.out.println("\nYou don't have any properties to build on.\n");
      }
   }
   
   public void BuySellBuildings(int choice)
   // PRE:  choice is set to 0 or 1
   // POST: User is allowed to select what Lot to buy/sell a house or hotel on. Choice
   //       indicates whether the user wants to buy (0) or sell (1) a building.
   {
      String[] lots;                // Set of all lots player can build on,
                                    //   e.g. {"Marvin Gardens", "Park Place"}
	   int menuChoice;               // Index of lots selected by the user, 
                                    //   0 <= menuChoice < lots.length
      int i;                        // Index of spaces when accessing all board locations
                                    //   0 <= i < spaces.length
      ActionsMenu menu;             // Menu to be displayed to user
      boolean sellBuilding;         // Indicator of user wanting to build or sell a building,
                                    //   true == user wants to sell building
                                    //   false == user doesn't want to sell building
      menu = new ActionsMenu();
      
      sellBuilding = false;   // Defaults to user not wanting to sell a building
      
      if (choice == 0)        // User wants to buy buildings
      {
         System.out.println("\nSelect a Lot to purchase a building on:");
      }
      else if(choice == 1)    // User wants to sell buildings
      {
         System.out.println("\nSelect a Lot to sell a building on.");
         sellBuilding = true;
      }
      
      lots = players[curPlayer - 1].GetBuildLots(sellBuilding);
      menuChoice = menu.runActionsMenu(lots);
      i = 0;
      
      while (spaces[i].GetName() != lots[menuChoice]) // Access all board squares to determine
      {                                               //   which player wanted to build on.
         i++;
      }
      
      if (choice == 0)        // User wants to buy buildings
      {
         ((Lot)spaces[i]).AddHouse();                 // House is added to lot
         players[curPlayer - 1].Pay(((Lot)spaces[i])
                               .GetHousePrice());     // Player pays for house
         
         System.out.println("You purchased a building on " 
                          + spaces[i].GetName() + "\n");
      }
      else if(choice == 1)    // User wants to sell buildings
      {
            ((Lot)spaces[i]).SellHouse();             // House is removed from lot
            
            System.out.println("You sold a building on " 
                             + spaces[i].GetName() + "\n");
      }
   }
   
   // ****** ACCESSORS ******
   
   public void PopUpAllBoardInfo()
   // POST: A popup window is created containing text describing the state of the game.
   {
      String result;             // Message displayed in popup window
      JTextArea area;            // Text area to hold the message
      JScrollPane pane;          // Window pane with scrollbar containing text area

      result = "";
      result += "------ PLAYER STATUS INFORMATION ------\n\n";
      
      // PURPOSE: To concatinate player information to result
      for(int i = 0; i < players.length; i++)
      {
         result += ("++ Player " + (i + 1) + " ++\n");
         result += (players[i].toString() + "\n\n");
      }
      
      result += "\n\n------ BOARD STATUS INFORMATION ------\n\n";
      
      // PURPOSE: To concatinate board information to result
      for(BoardLocation cur : spaces)
      {
         result += (cur + "\n\n");
      }
      
      area = new JTextArea(result);       // result is set to the popup window
      area.setRows(40); 
      area.setColumns(50);
      pane = new JScrollPane(area);
      JOptionPane.showMessageDialog(null, pane, "Monopoly Board Info.", JOptionPane.PLAIN_MESSAGE);   
   }
   
   public void PrintPlayerInfo()
   // POST: Outputs to console a String representation of all the Player elements in players.
   {
      System.out.println("\n------ PLAYER STATUS INFORMATION ------\n");
      for(int i = 0; i < players.length; i++)      // Iterates through each element in player
      {                                            //   outputs a representive String to console
         System.out.println("++ Player " + (i + 1) + " ++\n");
         System.out.println(players[i].toString() + "\n");
      }
   }
   
   public void PrintBoardInfo()
   // POST: Outputs to console a String representation of all the BoardLocation elements in 
   //       spaces.
   {
      System.out.println("\n------ BOARD STATUS INFORMATION ------\n");
      // PURPOSE: To print out all of the BoardLocations in order to the screen
      // REASON:  This loop will iterate through ever value in spaces (determinate),
      //             so an enhanced for loop is required
      for( BoardLocation cur: spaces)
      {
         System.out.println(cur + "\n");
      }
   }

   private int GetAmt(String amtString)
   // PRE:  amtString is initialized with the syntax '$XXX' where X denotes an integer
   // POST: FCTVAL == The integer representation of Monopoly dollar ammount stored in amtString
   {
      String trimmedString;         // Substring of amtString which holds characters '0' to '9',
                                    //   e.g. "342"
      int startIndex;               // Index of the '$' in amtString, startIndex >= 0
      int currentIndex;             // Index of the current element in amtString,  
                                    //   currentIndex >= 0
      startIndex = amtString.indexOf('$');
      currentIndex = startIndex + 1;
      
      while (currentIndex < amtString.length()
         && (amtString.charAt(currentIndex) >= '0') 
         && (amtString.charAt(currentIndex) <= '9')
         || (amtString.charAt(currentIndex) == '-'))
      {
         currentIndex++;
      }
      
      trimmedString = amtString.substring(startIndex + 1, currentIndex);
      return Integer.parseInt(trimmedString);
   }
   
   public boolean ParseAction(String action)
   // PRE:  action is initialized to a keyword in this set: {"Continue", "Buy $", "Pay rent $",
   //       "Declare bankruptcy", "Collect $", "Pay tax $", "End turn", "Pay bail $", 
   //       "Go to jail"}
   // POST: FCTVAL == true, the current player (players[curPlayer - 1]) did not lose and 
   //                 therefore the game continues
   //       FCTVAL == false, the current player lost and therefore the game is over
   //       The current player completes the desired action indicated by the value of action.
   //       action is searched for any of the keywords and, when a match is found, an action is 
   //       completed.
   {     
      int amt;          // Amount of money received or paid by player, amt > 0 in Monopoly $
      
      if (action.indexOf("Continue") != -1)                 // Player selected 'Continue'
      {
         return false;                                      // No action taken
      }
      else if (action.indexOf("Declare bankruptcy") != -1)  // Player declared bankruptcy
      {
         System.out.println("Player " + curPlayer 
                          + " has declared bankruptcy.\n"); // Player has no more money
         return true;                                       // Player lost
      }
      else if (action.indexOf("End turn") != -1)            // Player ended turn
      {
         System.out.println("You are in jail.\n");
         players[curPlayer - 1].AddTurnInJail();            // Player is in jail
         return false;                                      // No action taken
      }
      else if (action.indexOf("Go to jail") != -1)          // Player is sent to jail
      {
         players[curPlayer - 1].SetLocation(spaces[10]);    // Player location is set to Jail
         System.out.println("Go to jail. Go directly to jail."
                          + " Do not pass go. Do not collect $"
                          + ((GoSquare)spaces[0]).GetReward() + "\n");
         players[curPlayer - 1].ResetDoublesRolled();       // Player now has rolled zero
                                                            //   doubles
         players[curPlayer - 1].AddTurnInJail();            // First turn in jail
      }
      else if (action.indexOf("$") != -1)                   // Money is involved in keyword
      {
         amt = GetAmt(action);
         if (action.indexOf("Collect") != -1)               // If player is receiving money
         {
            if (action.indexOf("Free Parking") != -1)       // If Free Parking was landed on
            {
               ((FreeParkingSquare)spaces[20]).RemoveMoney();     // Money is removed from Free 
            }                                                     //   Parking
            
            System.out.println("You receive $" + amt + ".\n");
            players[curPlayer - 1].Receive(amt);
         }
         else if (action.indexOf("Buy") != -1)              // If player is buying a property
         {
            System.out.println("You bought " 
                             + (players[curPlayer - 1].GetLocation())
                               .GetName());
            players[curPlayer - 1].BuyProperty();
         }
         else if (action.indexOf("Pay rent") != -1)         // If player must pay an amount to
         {                                                  //   another player.
            System.out.println("You pay rent of $" + amt 
                  + " to player " 
                  + (((Property)(players[curPlayer - 1].GetLocation()))
                    .GetOwner()).GetToken() + "\n");
                    
            players[curPlayer - 1].Pay(amt);                   // Player pays rent
            (((Property)(players[curPlayer - 1].GetLocation()))
                                               .GetOwner())
                                               .Receive(amt);  // Player receives rent
         }
         else if (action.indexOf("Pay tax") != -1)
         {
            System.out.println("You paid the tax of $" + amt + "\n");
            ((FreeParkingSquare)spaces[20]).AddMoney(amt);  // Tax money goes to Free Parking
            return !(players[curPlayer - 1].Pay(amt));      // Removes amt from player's money 
         }                                                  
         else if (action.indexOf("Pay bail") != -1)
         {
            System.out.println("You are out of jail after"
                             + " paying bail of $" + amt + "\n");
            ((FreeParkingSquare)spaces[20]).AddMoney(amt);  // Tax money goes to Free Parking
            return !(players[curPlayer - 1].Pay(amt));      // Removes amt from player's money 
         }
      }
      
      return false;                                         // Game is not over
   }
}