//connect4Project
//Author: Huzaifa A.
//Date Modified: 06/16/2023
//Description: A fun and cool game to play with your friends, the objective is to connect 4 chips Vertically, Horizonatally, and Diagonally

//import files
import hsa.Console;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;

public class connect4FINAL {
    static Console s; //start console
    static Console c; //main board
    static Console i; //inputs by user


    public static void main(String[] args) throws IOException {



        boolean playAgain = true; //program/game loop

        while (playAgain == true) {
            String opponent;
            int ROWS = 7; //number of Rows +1
            int COLUMNS = 8; //number of Columns +1
            boolean winStatus = false; //win status determines weather a player has won
            String gameLoopInput; //user will choose if they would like to play again
            boolean availability; //boolean expressing weather selected spot is availble or not
            availability = false; //intialzing the booolean value, so it can enter the while loop
            int userColumn = 0; //column selected by user
            int totalTurns = 1; //counts how many turns the user had had
            int MAXMOVES = 42; //total amount of moves possible on a 6x7 board
            int roundNum = 1; //keeps track of round number
            int x;
            String winner;
            winner = " "; //saves the winner
            char[] boardTrack = new char[ROWS * COLUMNS]; //creates an array for all possible values on the board
            String player1, player2; //this determines player names etc
            player1 = " ";
            player2 = "";

            //initializing the board array
            for (x = 0; x < ROWS * COLUMNS; x++) {
                boardTrack[x] = ' '; //gives each chracter a blank value
            }

            s = new Console(); // start console
            // begins game
            startScreen(); //displays main screen
            rulesScreen(); //imports rules

            //console for inputs                                                                                                 
            i = new Console(); //input console
            player1 = addPlayer(); // addding player1
            opponent = selectOpponent(); //selecting opponent

            //console for digital output
            c = new Console(32, 76); // main board
            displayBoard(); //displays connect 4 board

            //game loop for second player
            if (opponent.indexOf("human") != -1) {
                player2 = addSecondPlayer();
                while (winStatus == false && totalTurns <= MAXMOVES) // if the winStatus is true or loop has reaches maximum number of moves it will end
                {
                    outputPlayers(player1, player2); //outputs players names and info
                    i.println();
                    i.println("Turn #" + roundNum); //outputs turn#

                    // player1
                    do {
                        i.println("Player 1" + "(" + player1 + ")" + ":" + "Please Pick a Column(R)"); //prompts player1 to input a colunmn
                        userColumn = validColumn(); //asks user to input a column betwwen 1 & 7
                        i.println();
                        availability = checkAvailable(userColumn, boardTrack, ROWS, COLUMNS); //checks if the valid column has an avaible position on the board
                        if (availability == true) //if it does the chip will be outputting
                        {
                            boardTrack = displayChip(userColumn, boardTrack, ROWS, COLUMNS, 'R'); //displays red chip for player 1
                        } else // if place not avaible then..
                        {
                            availability = false;
                        }
                    } while (availability == false); //user will be asked to input another column untill an availble column is inputted

                    totalTurns++; //keeps track of total turns

                    // Check if player 1 wins after their move
                    winStatus = connect4(userColumn, boardTrack); //checks board and sees is any 4 chips are in diagnol, horizontal, or vertical lines
                    if (winStatus == true) //if there is a connect 4, then game ends
                    {
                        break; //escapes loop if connect 4 found
                    }

                    // player2
                    do {
                        i.println("Player 2" + "(" + player2 + ")" + ":" + "Please Pick a Column(Y)"); //prompts player1 to input a colunmn
                        userColumn = validColumn(); //asks user to input a column betwwen 1 & 7
                        i.println();
                        availability = checkAvailable(userColumn, boardTrack, ROWS, COLUMNS); //checks if the valid column has an avaible position on the board
                        if (availability == true) //if it does the chip will be outputting
                        {
                            boardTrack = displayChip(userColumn, boardTrack, ROWS, COLUMNS, 'Y'); //displays yellow chip for player 2
                        } else // if place not avaible then..
                        {
                            availability = false;
                        }
                    } while (availability == false); //user will be asked to input another column untill an availble column is inputted

                    totalTurns++; //keeps track of number of turns
                    roundNum++; //keeps track of round number

                    // Check if player 2 wins after their move
                    winStatus = connect4(userColumn, boardTrack); //checks board and sees is any 4 chips are in diagnol, horizontal, or vertical lines
                    if (winStatus == true) //if there is a connect 4, then game ends
                    {
                        break; //escapes loop if connect 4 found
                    }

                    i.clear(); //clears console to make it more clear for next turn
                }

                // Check the win status after the loop ends
                if (winStatus == true) {
                    if (totalTurns % 2 == 0) //if number was a odd number the winner was user1
                    {
                        i.println("Player 1 (" + player1 + ") wins!"); //informs user2 they won     
                        winner = player1; //set winner value
                    } else //the winner was user2
                    {
                        i.println("Player 2 (" + player2 + ") wins!"); //informs user2 they won
                        winner = player2;
                    }
                } else {
                    i.println("It's a draw!"); //if no winner was decalared
                }

                outputScores(winner, opponent);

                i.println("Would you like to play again?"); //asks user if they would like to play again
                i.println("Y or N");
                gameLoopInput = i.readLine(); //user choice of playing again

                if (gameLoopInput.equalsIgnoreCase("Y") == true) //verifying choice of user
                {
                    playAgain = true; //back to old loop
                    i.close();
                    c.close();
                } else //thank user
                {
                    c.clear();
                    i.println("Thank you for playing");
                    playAgain = false;
                }

            } //game loop for second player


            //start of bot player
            else {
                while (winStatus == false && totalTurns <= MAXMOVES) // if the winStatus is true or loop has reaches maximum number of moves it will end
                {
                    outputPlayers(player1, player2); //outputs players names and info
                    i.println();
                    i.println("Turn #" + roundNum); //outputs turn#

                    // player1
                    do {
                        i.println("Player 1" + "(" + player1 + ")" + ":" + "Please Pick a Column(R)"); //prompts player1 to input a colunmn
                        userColumn = validColumn(); //asks user to input a column betwwen 1 & 7
                        i.println();
                        availability = checkAvailable(userColumn, boardTrack, ROWS, COLUMNS); //checks if the valid column has an avaible position on the board
                        if (availability == true) //if it does the chip will be outputting
                        {
                            boardTrack = displayChip(userColumn, boardTrack, ROWS, COLUMNS, 'R'); //displays red chip for player 1
                        } else // if place not avaible then..
                        {
                            availability = false;
                        }
                    } while (availability == false); //user will be asked to input another column untill an availble column is inputted

                    totalTurns++; //keeps track of total turns

                    // Check if player 1 wins after their move
                    winStatus = connect4(userColumn, boardTrack); //checks board and sees is any 4 chips are in diagnol, horizontal, or vertical lines
                    if (winStatus == true) //if there is a connect 4, then game ends
                    {
                        break; //escapes loop if connect 4 found
                    }

                    // bot
                    do {
                        userColumn = (int)(Math.random() * 7 + 1); //1-100                                           //bot inputs a column betwwen 1 & 7
                        i.println();
                        availability = checkAvailable(userColumn, boardTrack, ROWS, COLUMNS); //checks if the valid column has an avaible position on the board
                        if (availability == true) //if it does the chip will be outputting
                        {
                            boardTrack = displayChip(userColumn, boardTrack, ROWS, COLUMNS, 'Y'); //displays yellow chip for bot
                        } else // if place not avaible then..
                        {
                            availability = false;
                        }
                    } while (availability == false); //user will be asked to input another column untill an availble column is inputted

                    totalTurns++; //keeps track of number of turns
                    roundNum++; //keeps track of round number

                    // Check if bot wins after their move
                    winStatus = connect4(userColumn, boardTrack); //checks board and sees is any 4 chips are in diagnol, horizontal, or vertical lines
                    if (winStatus == true) //if there is a connect 4, then game ends
                    {
                        break; //escapes loop if connect 4 found
                    }

                    i.clear(); //clears console to make it more clear for next turn
                }

                // Check the win status after the loop ends
                if (winStatus == true) {
                    if (totalTurns % 2 == 0) //if number was a odd number the winner was user1
                    {
                        i.println("Player 1 (" + player1 + ") wins!"); //informs user2 they won     
                        winner = player1; //set winner value
                    } else //the winner was user2
                    {
                        i.println("Bot " + "wins!"); //informs user2 they won
                        winner = "Bot";
                    }
                } else {
                    i.println("It's a draw!");
                    winner = "Bot"; //if no winner was decalared
                }

                outputScores(winner, opponent);
                i.println("Would you like to play again?"); //asks user if they would like to play again
                i.println("Y or N");
                gameLoopInput = i.readLine(); //user choice of playing again

                //verifying choice of user
                if (gameLoopInput.equalsIgnoreCase("Y") == true) {
                    playAgain = true; //back to old loop
                    i.close();
                    c.close();
                } else //thank user
                {
                    c.clear();
                    i.println("Thank you for playing");
                    playAgain = false;
                }

            } //game loop for bot 



        } //full game loop 



    } // naim


    // start up screen
    public static void startScreen() {
        Image img;
        s.println("WELCOME TO CONNECT 4");
        //Try to import the image from the file
        try {

            img = ImageIO.read(new File("connect4.jpg"));
        } catch (IOException e) //File not found
        {
            img = null;
        }

        //Draw the image (Image, x location, y location, null)
        s.drawImage(img, 10, 60, null);

        s.println("Press any key to begin!!");
        s.readLine();
        s.clear();
    } // end of startScreen()


    // displays rules
    public static void rulesScreen() throws IOException {
        BufferedReader input; //calling reader
        input = new BufferedReader(new FileReader("TEXTFILES\\connect4Rules.txt")); //retreives rules.txt
        s.println("Welcome to Connect 4");
        s.println();
        int number;
        String[] array = new String[6]; //amount of lines
        number = 0;
        String importLine = input.readLine(); //reading from file

        array[number] = importLine; // importing lines into arrays
        s.println(array[number]);

        // Importing Rules
        while (importLine != null) { //importing files until null
            importLine = input.readLine();
            array[number] = importLine;
            s.println(array[number]);
            number++;
        }
        input.close();
        s.println();
        s.println("Press any key to continue!!"); //escaping start screen
        s.readLine();
        s.close();

    } // end of displayRules


    //selectOponent
    public static String selectOpponent() {
        String opponentChoice;

        i.println("Select Opponent");
        i.println("Bot or Human?");
        opponentChoice = i.readLine();

        return opponentChoice;
    } //end of selectOpponent


    // add first player
    public static String addPlayer() {
        String playerName;

        i.println("Enter Players Name"); //prompt to enter first player name
        playerName = i.readLine(); //user inputs first players name                                            
        i.clear();
        Player player1 = new Player(playerName, "red");
        return (playerName);
    } // end of addPlayer()

    // add second player
    public static String addSecondPlayer() {
        String playerName; //returns second player name 

        i.println("Enter Second Players Name"); //prompt to enter second player name
        playerName = i.readLine(); //user inputs second players name
        Player player2 = new Player(playerName, "yellow");
        i.clear();
        return (playerName); //returns secondPlayerName
    } // end of addSecondPlayer()

    //outputs players names
    public static void outputPlayers(String player1, String player2) {
        i.println("Welcome Player 1: " + player1);
        i.println("Welcome Player 2: " + player2);


    } //end of outputplayers

    //output scores
    public static void outputScores(String winner, String opponent) throws IOException {

        PrintWriter output;
        output = new PrintWriter(new FileWriter("scores.txt"));


        output.println("Game Stats ");

        //human vs human output
        if (opponent.indexOf("human") != -1) {
            output.println("For PLayer vs Player");
            output.println("Winner " + "is " + winner);
        }

        //human vs bot
        else {
            output.println("For PLayer vs Bots");
            output.println("Winner " + "is " + winner);
        }

        output.close();
    }
    //end of output scores



    // display connect4 board
    public static void displayBoard() {
        int counter;
        // board outline
        c.setColor(Color.blue);
        c.fillRect(15, 15, 575, 475);

        // column 1;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(30, counter, 60, 60);
        }

        // column 2;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(110, counter, 60, 60);
        }
        // column 3;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(190, counter, 60, 60);
        }
        // column 4;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(270, counter, 60, 60);
        }
        // column 5;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(355, counter, 60, 60);
        }
        // column 6;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(440, counter, 60, 60);
        }
        // column 7;
        for (counter = 35; counter < 411; counter = counter + 75) {
            c.setColor(Color.white);
            c.fillOval(520, counter, 60, 60);
        }

        c.setColor(Color.blue); //display column numbers
        c.print("       " + "1" + "         " + "2");
        c.print("         " + "3" + "         " + "4");
        c.print("         " + "5" + "         " + "6");
        c.print("         " + "7" + "         ");


        c.setColor(Color.black); //outline for circle
        c.drawOval(355, 530, 70, 70);
        c.drawOval(180, 525, 70, 70);

        c.setColor(Color.red); //red circle
        c.fillOval(355, 530, 70, 70);

        c.setColor(Color.yellow); //yellow circle
        c.fillOval(180, 525, 70, 70);


    } // end of displayBoard()


    //asks user for valid column
    public static int validColumn() {
        int column;
        column = getValidInt(1, 7); //asks user to input # 1-7
        return (column); //returns user column input to main method
    }
    //end of valid column


    // checkAvailble
    public static boolean checkAvailable(int userColumn, char boardTrack[], int ROWS, int COLUMNS) {

        int rowValid; //used to check individual rows
        for (rowValid = 1; rowValid <= 6; rowValid++) //checks each row of the indivual column to see which if any space is empty
        {

            if (boardTrack[rowValid * 8 + userColumn] == ' ') // if a spot is empty then the program will have that as an open spot
            {
                return (true);
            } //returns to main method as a avaible spot  
            else //if spot is not empty, then it moves on to next row as column is as full
            {}

        }
        i.println("Column is full!!"); //if all 6 rows are full
        return (false); //no space in this column, please selet antoher one

    } // end ofcheckAvailable



    // get valid int
    public static int getValidInt(int low, int high) {
        int num; //varaible attempting to create
        String valueStr; //string user inputs

        while (true) {
            valueStr = i.readLine(); //user inputs number 
            try {
                num = Integer.parseInt(valueStr); //tries to convert the string to integer value

                //if the number is less than or higher than expected values user will try again
                if (num >= low && num <= high) {
                    return (num);
                } else // Incorrect range
                {
                    i.println("Invalid selection. You must choose a number between " + low + " and " + high + ".");
                }
            } catch (NumberFormatException e) // Not a number
            {
                i.println("Invalid selection. " + valueStr + " is not a number."); //Not a number
            }

        }
    } // end of getValidInt


    // displayChip
    public static char[] displayChip(int userColumn, char boardTrack[], int ROWS, int COLUMNS, char C) {
        int rowValid;

        //indidually checks which row is empty in that column
        for (rowValid = 1; rowValid <= 7; rowValid++) {
            if (boardTrack[rowValid * 8 + userColumn] == ' ') //empty space found
            {
                boardTrack[rowValid * 8 + userColumn] = C; // place a Char place holder in the position
                break;
            } else {}

        }

        int valueCol = rowValid; //declaring column priting variable for case statement
        int valueRow = userColumn; //declaring row priting variable for case statement
        int xInt; // declaring the x-int for the chip 
        int yInt; //declaring the y-int for the chip
        xInt = 30; //default value for x-int
        yInt = 35; //default values for y-int


        //finding the yint/column value for printing
        switch (valueRow) {
            case 1:
                xInt = 30;
                break;
            case 2:
                xInt = 110;
                break;
            case 3:
                xInt = 190;
                break;
            case 4:
                xInt = 270;
                break;
            case 5:
                xInt = 355;
                break;
            case 6:
                xInt = 440;
                break;
            case 7:
                xInt = 520;
                break;
            default:
                i.println("Error");
                break;
        }

        //finding the yint/column value for printing
        switch (valueCol) {
            case 1:
                yInt = 410;
                break;
            case 2:
                yInt = 335;
                break;
            case 3:
                yInt = 260;
                break;
            case 4:
                yInt = 185;
                break;
            case 5:
                yInt = 110;
                break;
            case 6:
                yInt = 35;
                break;
            default:
                i.println("Error");
                break;
        }
        if (C == 'R') {
            c.setColor(Color.red); //set color to red for first player
        } else if (C == 'Y') {
            c.setColor(Color.yellow); //set color to yellow for second  player
        }

        c.fillOval(xInt, yInt, 60, 60); //draws chip
        return (boardTrack); //returns array back with filled in value

    } // end of displayChip


    public static boolean connect4(int userColumn, char[] boardTrack) {
        // Vertical check
        String redWin = "RRRR"; // If connect 4 pattern for Red
        String yellowWin = "YYYY"; // If Connect 4 pattern for Yellow
        String[] vString = new String[8]; // Array created for each vertical column values
        String[] hString = new String[8]; // Array created for each horizontal row
        boolean winFound = false;
        int initialRow, initialColumn; //starting row & column
        int dRow, dColumn;
        int x; //loops(Diagonal Position Loops)
        String diagonalCheck;



        // Initialize vString array
        for (int v = 0; v < 8; v++) {
            vString[v] = "";
        }

        // Initialize hString array
        for (int h = 0; h < 8; h++) {
            hString[h] = "";
        }


        // Vertical check
        for (int column = 1; column < 8; column++) { // Changes column values so the string can be created per column
            for (int row = 1; row < 7; row++) { // Changes row values so the rows can be outputted to string to be checked    
                if (boardTrack[(row * 8) + column] == 'R') { // If row value is 'R'
                    vString[column] = vString[column].concat("R"); // Add "R" to the string as a placeholder for Red
                } else if (boardTrack[(row * 8) + column] == 'Y') { // If row value is 'Y'
                    vString[column] = vString[column].concat("Y"); // Add "Y" to the string as a placeholder for Yellow
                }
            } // End of row loop
        } // End of column loop

        for (int column = 1; column < 8; column++) { // Checking each individual column string
            if (vString[column].indexOf(redWin) != -1 || vString[column].indexOf(yellowWin) != -1) { // Check if the diagonalCheck match winRed or winYellow
                winFound = true;
                break;
            }
        }

        // Horizontal check                                
        for (int row = 1; row < 7; row++) { // Changes row values so the string can be inputted per column
            for (int column = 1; column < 8; column++) { // Changes column values so the column values can be added to a single column string
                if (boardTrack[(row * 8) + column] == 'R') { // If column value is 'R'
                    hString[row] = hString[row].concat("R"); // Add "R" to the string
                } else if (boardTrack[(row * 8) + column] == 'Y') { // If column value is 'Y'
                    hString[row] = hString[row].concat("Y"); // Add "Y" to the string
                }
            }
        }

        for (int row = 1; row < 7; row++) { // Switching row values to access strings for patterns
            if (hString[row].indexOf(redWin) != -1 || hString[row].indexOf(yellowWin) != -1) // Check if the diagonalCheck match winRed or winYellow
            {
                winFound = true;
                break;
            }
        }

        //Diagonal Check                                                                                     // Check diagonals - T L to B R
        for (initialRow = 1; initialRow < 4; initialRow++) {
            for (initialColumn = 1; initialColumn < 5; initialColumn++) {
                // an empty string to store characters
                diagonalCheck = " ";

                // Loop through four positions in the diagonal
                for (x = 0; x < 4; x++) {
                    // Calculate the row and column indexes for each position
                    dRow = initialRow + x;
                    dColumn = initialColumn + x;

                    // If character at the position is 'R', add 'R' to the diagonalCheck
                    if (boardTrack[(dRow * 8) + dColumn] == 'R') {
                        diagonalCheck = diagonalCheck.concat("R");
                    }
                    // If  character at the position is 'Y', add 'Y' to the diagonalCheck
                    else if (boardTrack[(dRow * 8) + dColumn] == 'Y') {
                        diagonalCheck = diagonalCheck.concat("Y");
                    }
                }

                // Check if the diagonalCheck match winRed or winYellow
                if (diagonalCheck.equals(redWin) || diagonalCheck.equals(yellowWin)) {
                    winFound = true;
                    break;
                }
            }
            if (winFound) { //If found, no need for next loop
                break;
            }
        }

        // Check diagonals - T L to B 
        for (initialRow = 1; initialRow < 4; initialRow++) {
            for (initialColumn = 4; initialColumn < 8; initialColumn++) {
                // an empty string to store characters 
                diagonalCheck = "";

                // Loop through four positions in the diagonal
                for (x = 0; x < 4; x++) {
                    // Calculate the row and column indexes for each position
                    dRow = initialRow + x;
                    dColumn = initialColumn - x;

                    // If the character at the position is 'R', add "R" to the diagonalCheck
                    if (boardTrack[(dRow * 8) + dColumn] == 'R') {
                        diagonalCheck = diagonalCheck.concat("R");
                    }
                    // If the character at the position is 'Y', add '"Y" to the diagonalCheck
                    else if (boardTrack[(dRow * 8) + dColumn] == 'Y') {
                        diagonalCheck = diagonalCheck.concat("Y");
                    }
                }

                // Check if the diagonalCheck match winRed or winYellow
                if (diagonalCheck.equals(redWin) || diagonalCheck.equals(yellowWin)) {
                    winFound = true;
                    break;
                }
            }
            if (winFound) {
                break;
            }
        }

        return (winFound); //returnt connect4 result/ winResult back to user
    }

} //ssalc