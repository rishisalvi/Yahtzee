import java.util.Scanner;
/**
 *	Introduce the game here
 *
 *	@author	
 *	@since	
 */
 
public class Yahtzee {
	private YahtzeePlayer player1;
	private YahtzeePlayer player2; 
	private int firstPlayer;

	public Yahtzee(){
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		firstPlayer = 0;
	}
	public static void main(String[] args){
		Yahtzee ya = new Yahtzee();
		ya.run();
	}
	
	public void run(){
		printHeader();
		getPlayerNames();
		firstPlayer = getFirstPlayer();
		printCard();
	}

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

	public void getPlayerNames(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Player 1, please enter your first name : ");
		player1.setName(scan.nextLine());
		System.out.print("Player 2, please enter your first name : ");
		player2.setName(scan.nextLine());
	}

	public int getFirstPlayer(){
		DiceGroup dg = new DiceGroup();
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
			dg.rollDice();
			dg.printDice();
			score1 = dg.getTotal();
			System.out.print("\nLet's see who will go first. " + p2 + 
				", please hit enter to roll the dice : ");
			dg.rollDice();
			dg.printDice();
			score2 = dg.getTotal();
			if (score1 == score2)
				System.out.println("Whoops, we have a tie (both rolled " + score1 + 
				"). Looks like we'll have to try that again . . .");
			else
				firstFound = true;
		}
		System.out.println();
		System.out.println(p1 + ", you rolled a sum of " + score1 + ", and " + p2 +
			", you rolled a sum of " + score2);
		if (total1 > total2){
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

	public void printCard(){
		YahtzeeScoreCard ysc1 = player1.getScoreCard();
		YahtzeeScoreCard ysc2 = player2.getScoreCard();
		ysc1.printCardHeader();
		ysc1.printPlayerScore(player1);
		ysc2.printPlayerScore(player2);
	}
}