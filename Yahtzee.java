import java.util.Scanner;
/**
 *	Yahtzee is a game where two players roll 5 die and try to get the highest score.
 *  There are 13 scoring categories, including yahtzee, large/small straights, full
 *  house, etc. The 5 die have to match a certain criteria to get all of the points
 *  for a category. Each player is responsible for rolling the dice in order to 
 *  meet these criteria.
 *
 *	@author	Rishi Salvi
 *	@since	10/03/23
 */
 
public class Yahtzee {
	private YahtzeePlayer player1; // player 1 (contains name and scorecard)
	private YahtzeePlayer player2; // player 2 (contains name and scorecard)
	private int firstPlayer; // what player has the first turn
    private DiceGroup dg; // the 5 dice which are being rolled

	public Yahtzee(){
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		firstPlayer = 0;
        dg = new DiceGroup();
	}
	public static void main(String[] args){
		Yahtzee ya = new Yahtzee();
		ya.run();
	}
	
    /**
     * runs the game - calls all of the methods in order to proceed
     */
	public void run(){
		printHeader();
		getPlayerNames();
		firstPlayer = getFirstPlayer();
		printCard();
		for (int i = 1; i < 14; i++){
			System.out.println("\nRound " + i + " of 13 rounds.\n");
			if (firstPlayer == 1){
				playTurn(player1);
				playTurn(player2);
			}
			else{
				playTurn(player2);
				playTurn(player1);
			}
		}
		printFinal();
	}

    /**
     * responsible for printing the header that provides the users with the instructions
     * for the game
     */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

    /**
     * prompts for the users' names and stores them in their respective Objects
     * for later use
     */
	public void getPlayerNames(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Player 1, please enter your first name : ");
		player1.setName(scan.nextLine());
		System.out.print("\nPlayer 2, please enter your first name : ");
		player2.setName(scan.nextLine());
	}

    /**
     * decides which player is going first
     * rolls the dice once for each player and the higher score is the one that 
     * goes first
     * in case of a tie, the dice are rerolled until one score is higher than the other
     * @return      which player's turn is first
     */
	public int getFirstPlayer(){
		dg = new DiceGroup();
		Scanner scan = new Scanner(System.in);
		boolean firstFound = false; 
		String p1 = player1.getName();
		String p2 = player2.getName();
		int score1 = 0; 
		int score2 = 0;
		int firstPlayer = 0;
		while (!firstFound){
			System.out.print("\nLet's see who will go first. " + p1 + 
				", please hit enter to roll the dice : ");
			scan.nextLine(); // allows code to continue after user input
			dg.rollDice();
			dg.printDice();
			score1 = dg.getTotal();
			System.out.print("\n" + p2 + ", it's your turn. " +
				"Please hit enter to roll the dice : ");
			scan.nextLine();
			dg.rollDice();
			dg.printDice();
			score2 = dg.getTotal();
			if (score1 == score2) // equal scores
				System.out.println("Whoops, we have a tie (both rolled " + score1 + 
				"). Looks like we'll have to try that again . . .");
			else
				firstFound = true;
		}
		System.out.println();
		System.out.println(p1 + ", you rolled a sum of " + score1 + ", and " + p2 +
			", you rolled a sum of " + score2);
		if (score1 > score2){
			firstPlayer = 1;
			System.out.print(player1.getName());
		}
		else{
			firstPlayer = 2;
			System.out.print(player2.getName());
		}
		System.out.println(", since your sum was higher, you'll roll first.");
		return firstPlayer;
	}

    /**
     * responsible for printing out the scorecard each time a player's score is 
     * updated
     */
	public void printCard(){
		YahtzeeScoreCard ysc1 = player1.getScoreCard();
		YahtzeeScoreCard ysc2 = player2.getScoreCard();
		ysc1.printCardHeader();
		ysc1.printPlayerScore(player1);
		ysc2.printPlayerScore(player2);
	}

    /**
     * controls each turn for each player
     * two parts - rolling the die and selecting the score category
     * @param player    what player's turn it is
     */
	public void playTurn(YahtzeePlayer player){
		finishRolling(player);
        selectCategory(player);
	}

    /**
     * rolls the dice for the player
     * has an option to reroll certain dice if they want up to 2 times
     * @param player    what player's turn it is
     */
    public void finishRolling(YahtzeePlayer player){
        dg = new DiceGroup();
        Scanner scan = new Scanner(System.in);
        String name = player.getName();

        System.out.printf("\n" + name + ", it's your turn to play. Please hit " +
            "enter to roll the dice : ");
        scan.nextLine(); // allows code to continue after user input
        dg.rollDice();
        dg.printDice();
        System.out.println("\nWhich di(c)e would you like to keep?  Enter the" +
            " values you'd like to 'hold' without spaces");
        System.out.println("For examples, if you'd like to 'hold' die 1, 2," +
            " and 5, enter 125");
        System.out.print("(enter -1 if you'd like to end the turn) : ");
        String current = scan.nextLine();

        if (!current.equals("-1")) { // reroll
            dg.rollDice(current);
            dg.printDice();
            System.out.println("\nWhich di(c)e would you like to keep?  Enter the" +
                " values you'd like to 'hold' without spaces");
            System.out.println("For examples, if you'd like to 'hold' die 1, 2," +
                " and 5, enter 125");
            System.out.print("(enter -1 if you'd like to end the turn) : ");
            current = scan.nextLine();
            if (!current.equals("-1")) { // reroll
                dg.rollDice(current);
                dg.printDice();
            }
        }
    }

    /**
     * user is able to select which category they want to be scored on depending on
     * the roll of their dice
     * checks if the category they selected is valid (ex. hasn't been selected before)
     * and if it is, sets its value to the result of the roll
     * @param player    what player's turn it is
     */
    public void selectCategory(YahtzeePlayer player){
        printCard();
        String name = player.getName();
        System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
        System.out.print(name +", now you need to make a choice. Pick a valid " +
            "integer from the list above : ");

        Scanner scan = new Scanner(System.in);
        boolean isValid = false;
        YahtzeeScoreCard sc = player.getScoreCard();

        while (!isValid){
            int category = scan.nextInt();
            if (category > 0 && category < 14) {
                if (sc.changeScore(category, dg)) 
                   isValid = true;
                else
                    System.out.print("Pick a valid integer from the list above : ");
            }
            else {
                System.out.print("Pick a valid integer from the list above : ");
            }
        }

        printCard();
    }

    /**
     * prints the final scores for both of the players after all of the rounds
     */
    public void printFinal(){
        int score1 = 0;
        String name1 = player1.getName();
        int score2 = 0;
        String name2 = player2.getName();

        for (int i = 1; i < 14; i++){
            score1 += player1.getScoreCard().getScore(i);
            score2 += player2.getScoreCard().getScore(i);
            
        }

        System.out.println(name1 + " had a score of " + score1);
        System.out.println(name2 + " had a score of " + score2);
        System.out.println();
    }
}