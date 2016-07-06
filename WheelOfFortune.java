/**
 * Submitted to: Mr. Ganuelas
 * Submitted by: Brandon Wong
 * Submitted on: Friday, October 7, 2011
 * Course Code:  ICS4U1
 * Description:  This application simulates the gameshow "Wheel of Fortune". It includes a main menu, and the
 *               game mode allows two players to take turns inputting letter guesses to try and solve a secret
 *               word puzzle.
 */

import java.util.*;
import java.lang.*;

public class WheelOfFortune {

		/*---------------------------------------------------------------------------------------------
		 *NOTE: Arrays and their related variables are declared as static as they are involved in the
		 *function of several methods. Also, various other key variables are also declared as static
		 *due to the limitation of only being able to return one variable/type per method, and also for
		 *the sake of keeping method parameters relatively neat. Actual use of the arrays and variables
		 *occurs mainly in the game method and thus will be described there in further detail.
		 *---------------------------------------------------------------------------------------------*/

		/*creates array which contains all possible phrases and their corresponding categories
		 *which may be selected to be solved in the game.*/
		static String[][] phraseList = {
			{"THE PYRAMIDS OF GIZA","Famous Landmark"},
			{"THE GREAT WALL OF CHINA","Famous Landmark"},
			{"MOUNT EVEREST","Famous Landmark"},
			{"THE EMPIRE STATE BUILDING","Famous Landmark"},
			{"THE LEANING TOWER OF PISA","Famous Landmark"},
			{"CHOCOLATE CHIP COOKIES","Dessert"},
			{"STRAWBERRY SHORTCAKE","Dessert"},
			{"BLUEBERRY PIE","Dessert"},
			{"BANANA SPLIT","Dessert"},
			{"THE DARK KNIGHT","Movie"},
			{"THE DAY AFTER TOMORROW","Movie"},
			{"PAPUA NEW GUINEA","Country"},
			{"DOMINICAN REPUBLIC","Country"},
			{"NETHERLANDS","Country"},
			{"SEYCHELLES","Country"},
			{"PHILIPPINES","Country"},
			{"A PIECE OF CAKE","Idiom"},
			{"ACTIONS SPEAK LOUDER THAN WORDS","Idiom"},
			{"BACK TO SQUARE ONE","Idiom"},
			{"BREAK A LEG","Idiom"},
			{"CLOSE BUT NO CIGAR","Idiom"},
			{"CROSS YOUR FINGERS","Idiom"},
			{"GOING OUT ON A LIMB","Idiom"},
			{"LAST BUT NOT LEAST","Idiom"},
			{"PRACTICE MAKES PERFECT","Idiom"},
			{"A RULE OF THUMB","Idiom"},
		};

		//variables associated with randomly selecting a phrase
		static int rangePhrase;
		static int randPhrase;
		static String secretPhrase;

		//creates array which will contain each letter of the secret phrase as a separate element
		static String[] SP = new String[1];

		//creates array used to generate the puzzle display for the user
		static String[] wordDisplay = new String[1];

		//creates array which contains all possible results from spinning the wheel
		static int[] spinList = {
			100,
			200,
			250,
			250,
			300,
			400,
			500,
			500,
			600,
			700,
			750,
			750,
			800,
			900,
			1000,
			1250,
			1500,
			2000,
			1,    //1: Bankrupt
			1,
			2     //2: Lose a Turn
		};

		//declarations of variables, notably those concerning player names and scores:
		static int roundNumber = 0;
		static int player;
		static String playerName;
		static String p1Name;
		static String p2Name;
    	static int p1RoundTotal = 0;
    	static int p2RoundTotal = 0;
    	static int p1MoneyTotal = 0;
    	static int p2MoneyTotal = 0;
    	static int moneyWon = 0;
    	static int moneyLost = 0;
    	static int wheelMoney;
    	static int vowelBought;
    	static int numCorrect;


	//MAIN METHOD: Rather insignificant.
	public static void main(String[] args) {
		MainMenu();
	}


	/*MAIN MENU: Contains the game's main menu options, prompting the user to either begin playing
	 *the game, read the instructions, or end the program.*/
	public static void MainMenu(){
		Scanner input = new Scanner (System.in);
		while (true){
			System.out.println("");
			StarLine();
			System.out.println ("~~~~~WHEEL OF FORTUNE~~~~~\n");
    		System.out.println ("[1] Play");
    		System.out.println ("[2] Instructions");
    		System.out.println ("[3] Quit");
    		System.out.print ("Enter the number of your choice: ");
    		int mainMenuChoice = input.nextInt();
    		switch (mainMenuChoice){
    			case 1:
					PlayGame();
    				break;
    			case 2:
					Instructions();
    				break;
    			case 3:
					System.exit(0);
    				break;
			}
		}
	}


	/*INSTRUCTIONS: Displays instructions on how to play 'Wheel of fortune', then prompts the user to
	 *return to the main menu method.*/
	public static void Instructions() {
		Scanner input = new Scanner (System.in);
		System.out.println("");
		StarLine();
		System.out.println("~~~~~INSTRUCTIONS~~~~~\n");
		System.out.println("What is Wheel of Fortune?\n");
		System.out.println("  - Similar to Hangman, players are given a category and a blank word puzzle, with each blank representing a");
		System.out.println("    letter in the answer. You must guess one letter at a time to try and be the first person to solve the");
		System.out.println("    puzzle.");
		System.out.println("\nGameplay Options:\n");
		System.out.println("  - On his/her turn, a player is given 3 options:");
		System.out.println("      1) You can spin the wheel, which will display a dollar amount; you then guess a consonant, and earn the");
		System.out.println("         value of your spin multiplied by the number of times the guessed letter appears in the puzzle.");
		System.out.println("      2) You can buy a vowel for $250.");
		System.out.println("      3) You can attempt to solve the entire puzzle.");
		System.out.println("\nGameplay Mechanics:\n");
		System.out.println("  - If you correctly guess a consonant or vowel, it will remain your turn and you can choose another action.");
		System.out.println("    If you incorrectly guess, it becomes the other player's turn.");
		System.out.println("  - If you spin the wheel and land on \"BANKRUPT\", you will lose your turn and all of your money. If you");
		System.out.println("    land on \"LOSE A TURN\", you will only lose your turn.");
		System.out.println("  - If you correctly solve the puzzle, you will win the round and keep any money earned. The losing player");
		System.out.println("    does not keep any money earned.");
		System.out.print("\nPress ENTER to return to main menu");
		String enter = input.nextLine();
	}


	/*PLAY GAME: Initializes the conditions for each round (secret phrase, player scores for the round, etc.), then
	 *prompts the player to select one of the main gameplay options (spin the wheel, buy a vowel, solve the puzzle).
	 *Meanwhile, it also keeps track of player turns and scores after each turn.*/
    public static void PlayGame() {
		Scanner input = new Scanner (System.in);

		//randomly selects one of the possible secret phrases to be used for this round
		rangePhrase = phraseList.length;
		randPhrase = (int)(rangePhrase * Math.random());
		secretPhrase = phraseList[randPhrase][0];

		//specifies the length of arrays used for display to match the length of the secret phrase
		SP = new String[secretPhrase.length()];
		wordDisplay = new String[secretPhrase.length()];

		//creates array containing each letter of the secret phrase as a separate element
		for (int i = 0; i < secretPhrase.length(); i++){
			SP[i] = secretPhrase.substring(i,i+1);
		}

		//prompts players to input their names (only before the first round
		roundNumber ++;
		if (roundNumber == 1){
			System.out.print("\nEnter Player 1 Name: ");
			p1Name = input.nextLine();
			System.out.print("Enter Player 2 Name: ");
			p2Name = input.nextLine();
		}

		//randomly selects which player will go first
		int randPlayer = (int)(2 * Math.random() + 1);
		player = randPlayer;
		if (player == 1){
			playerName = p1Name;
		}
		else{
			playerName = p2Name;
		}

		System.out.println("");
		StarLine();
		System.out.println("~~~~~ROUND " + roundNumber + "~~~~~\n");

		//creates initial puzzle display, that displays all letters from the secret phrase as dashes
		for (int i = 0; i < secretPhrase.length(); i++)
    	{
    		if (SP[i].equals(" ")){
    			wordDisplay[i] = " ";
    		}
    		else{
    			wordDisplay[i] = "-";
    		}
    	System.out.print(wordDisplay[i]);
    	}

    	System.out.println ("");
    	String guessListCons = "";
    	String guessListVowel = "";
    	boolean loseTurn = false;

		/*This loop displays gameplay options and updated information after each turn, and prompts the
		 *player to choose an action. It is technically infinite, and the only way to end the sequence
		 *of turns is to correctly solve the puzzle.*/
		while (true){
			System.out.println ("\n" + p1Name + ": $" + p1RoundTotal);
			System.out.println (p2Name + ": $" + p2RoundTotal);
			System.out.println ("\n" + playerName.toUpperCase() + "'S TURN:\n");
    		System.out.println ("[1] Spin the Wheel\t\t\t\t\t\t\t\tCategory: " + phraseList[randPhrase][1]);
    		System.out.println ("[2] Buy a vowel\t\t\t\t\t\t\t\t\tConsonants Guessed:\t" + guessListCons);
    		System.out.println ("[3] Solve the puzzle\t\t\t\t\t\t\tVowels Guessed:\t\t" + guessListVowel);
    		System.out.print ("\nEnter the number of your choice: ");
    		int playerAction = input.nextInt();
    		switch (playerAction){
    			case 1:
					loseTurn = WheelSpin(loseTurn);
					if(loseTurn == false){
					guessListCons = GuessALetter(guessListCons);
					}
					loseTurn = false;
    			break;
    			case 2:
    				if((player == 1 && p1RoundTotal >= 250) || (player == 2 && p2RoundTotal >= 250)){
    					guessListVowel = BuyAVowel(guessListVowel);
    				}
    				else{
    					System.out.println("**You do not have enough money!**\n");
    					StarLine();
    					RedisplayPhrase();
    					numCorrect = -1;
    				}
    			break;
    			case 3:
    				SolveThePuzzle();
    			break;
    		}

			//Player turn and score is updated at the end of each turn
    		moneyWon = wheelMoney * numCorrect;
			if (player == 1){
				p1RoundTotal += moneyWon - vowelBought;
				if (numCorrect == 0){
					player = 2;
					playerName = p2Name;
				}
			}
			else{
				p2RoundTotal += moneyWon - vowelBought;
				if (numCorrect == 0){
					player = 1;
					playerName = p1Name;
				}
			}
    		moneyWon = 0;
			wheelMoney = 0;
			numCorrect = 0;
			vowelBought = 0;
		}
    }


	/*WHEEL SPIN: Randomly selects a dollar value from the wheel array and displays it.
	 *Additionally, 'BANKRUPT' and 'LOSE A TURN' trigger a boolean which skips the letter-guessing method.*/
	public static boolean WheelSpin(boolean loseTurn){
		int rangeSpin = spinList.length;
		int randSpin = (int)(rangeSpin * Math.random());
		if (spinList[randSpin] == 1){
			if (player == 1){
				p1RoundTotal = 0;
			}
			else{
				p2RoundTotal = 0;
			}
			loseTurn = true;
			System.out.println("\nWHEEL SPIN: ~~BANKRUPT!~~\n");
			StarLine();
			RedisplayPhrase();
		}
		else if (spinList[randSpin] == 2){
			loseTurn = true;
			System.out.println("\nWHEEL SPIN: ~~LOSE TURN!~~\n");
			StarLine();
			RedisplayPhrase();
		}
		else{
			wheelMoney = spinList[randSpin];
			System.out.println("\nWHEEL SPIN: ~~$" + wheelMoney + "~~\n");
		}
	return (loseTurn);
	}


	/*GUESS A LETTER: Prompts the player to guess a consonant. The program then scans the array of letters
	 *in the secret phrase, and if there is a match to the guessed letter then the dash from the display array
	 *at that same index is replaced by the guessed letter. During this process the puzzle is thus updated and
	 *redisplayed. If the input is not a consonant or has already been guessed, then an error message is shown
	 *and the player is reprompted for input. Lastly, it adds the guessed letter to a string which stores
	 *previous guesses for reference, and returns this string for display in the main game method.*/
    public static String GuessALetter(String guessListCons){
		Scanner input = new Scanner (System.in);
		String guessCons = "";
		boolean invalid = true;
		do{
			System.out.print("Enter a letter guess: ");
			guessCons = input.nextLine();
			guessCons = guessCons.toUpperCase ();
			System.out.println ("");
			if(guessCons.equals("A") || guessCons.equals("E") || guessCons.equals("I") || guessCons.equals("O") || guessCons.equals("U")){
				System.out.println("**Your guess must be a consonant**");
			}
			else{
				if(guessListCons.indexOf(guessCons) != -1){
					System.out.println("**This letter has already been guessed!**");
				}
				else{
					StarLine();
					for (int i = 0; i < secretPhrase.length(); i++){
    					if(wordDisplay[i].equals("-") && SP[i].equals(guessCons)){
    						wordDisplay[i] = guessCons;
    						numCorrect++;
    					}
    				System.out.print(wordDisplay[i]);
    				invalid = false;
    				}
				}
			}
		}while (invalid);
		System.out.println ("");
		guessListCons += guessCons;
		return (guessListCons);
    }


	/*BUY A VOWEL: Nearly identical in coding to the guess-a-letter method. Differences include the setup
	 *for a reduction of $250 from the current player's money total, as well as minor code variations for
	 *detecting errors.*/
	public static String BuyAVowel(String guessListVowel){
		Scanner input = new Scanner (System.in);
		String guessVowel = "";
		boolean invalid = true;
		do{
			System.out.print("Enter a vowel guess: ");
			guessVowel = input.nextLine();
			guessVowel = guessVowel.toUpperCase ();
			System.out.println ("");
			if(guessVowel.equals("A") || guessVowel.equals("E") || guessVowel.equals("I") || guessVowel.equals("O") || guessVowel.equals("U")){
				if(guessListVowel.indexOf(guessVowel) != -1){
					System.out.println("**This letter has already been guessed!**");
				}
				else{
					StarLine();
					for (int i = 0; i < secretPhrase.length(); i++)
    				{
    					if(wordDisplay[i].equals("-") && SP[i].equals(guessVowel)){
    						wordDisplay[i] = guessVowel;
    						numCorrect++;
    					}
    					System.out.print(wordDisplay[i]);
    					invalid = false;
    				}
				}
			}
			else{
				System.out.println("**Your guess must be a vowel**");
			}
		}while (invalid);
		System.out.println ("");
		guessListVowel += guessVowel;
		vowelBought = 250;
		return (guessListVowel);
	}


	/*SOLVE THE PUZZLE: Prompts the player for the solution to the puzzle and displays the appropriate message.
 	 *If correct, this method branches off into the endgame method. Otherwise, the turn ends and normal gameplay
 	 *continues.*/
	public static void SolveThePuzzle(){
		Scanner input = new Scanner (System.in);
		System.out.print("Solve the puzzle: ");
		String guessPuzzle = input.nextLine();
		guessPuzzle = guessPuzzle.toUpperCase ();
		if(guessPuzzle.equals(secretPhrase)){
    		System.out.println("\n" + secretPhrase + "\n");
    		System.out.println("Correct! :)\n");
    		EndGame();
    	}
    	else{
    		System.out.println("\nWrong! :(\n");
    		StarLine();
    		RedisplayPhrase();
    	}
	}


	/*ENDGAME: Displays winner and final score results from the round. The winning player's score from the round
	 *is added to set of grand totals which account for the results of all rounds played. The player is then
	 *prompted to either begin another round (which keeps player names and grand totals and increments round number)
	 *or to return to the main menu (which resets all data).*/
	public static void EndGame(){
		Scanner input = new Scanner (System.in);
		StarLine();
		System.out.println("Congratulations " + playerName + "!\n");
		if (player == 1){
			System.out.println("Winnings: $" + p1RoundTotal);
			p1MoneyTotal += p1RoundTotal;
		}
		else{
			System.out.println("Winnings: $" + p2RoundTotal);
			p2MoneyTotal += p2RoundTotal;
		}
		p1RoundTotal = 0;
    	p2RoundTotal = 0;
		System.out.println("\nCurrent Totals:\n");
		System.out.println(p1Name + ": $" + p1MoneyTotal);
		System.out.println(p2Name + ": $" + p2MoneyTotal);
		System.out.println ("\n[1] Continue to Next Round");
    	System.out.println ("[2] Return to Main Menu");
    	System.out.print ("Enter the number of your choice: ");
    	int endGameChoice = input.nextInt();
    	switch (endGameChoice){
    		case 1:
				PlayGame();
    			break;
    		case 2:
    			p1MoneyTotal = 0;
    			p2MoneyTotal = 0;
    			roundNumber = 0;
				MainMenu();
    			break;
		}
	}


	/*REDISPLAY PHRASE: Used in any case where the puzzle has not changed but needs to be displayed
	 *(i.e. 'bankrupt', 'lose a turn', failed attempt to solve the puzzle)*/
	public static void RedisplayPhrase(){
		for (int i = 0; i < secretPhrase.length(); i++){
    		System.out.print(wordDisplay[i]);
    	}
    	System.out.println("");
	}


	/*STARLINE: Displays a long row of asterisks, used to distinguish recently updated/displayed information from
	 *previously displayed information; basically, they indicate new 'screens'.*/
	public static void StarLine(){
		System.out.println("*************************************************************************************************************\n");
	}
}