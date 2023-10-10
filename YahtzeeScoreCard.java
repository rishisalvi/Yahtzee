/**
 *	The scorecard consists of a 3 by 13 grid. The rows are the labels and the names
 * 	of both of the players. Each of the columns indicates the score of each player
 * 	for each type of roll. A blank space means they haven't received a score for that
 * 	category and it is still open to be scored. If there is a number, that is their
 * 	score for the category
 *
 *	@author	Rishi Salvi
 *	@since	10/03/23
 */
public class YahtzeeScoreCard {
	private int[] scores; // array containing all of the scores for each category for one player
	
	public YahtzeeScoreCard(){
		scores = new int[13]; 
		for (int i = 0; i < 13; i++)
			scores[i] = -1; // setting them so they appear blank
	}
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(int i) {
		return scores[i - 1];
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		if (scores[choice - 1] != -1)
			return false;
		if (choice <= 6)
			numberScore(choice, dg); 
		else if (choice == 7)
			threeOfAKind(dg); 
		else if (choice == 8)
			fourOfAKind(dg); 
		else if (choice == 9)
			fullHouse(dg); 
		else if (choice == 10)
			smallStraight(dg); 
		else if (choice == 11)
			largeStraight(dg); 
		else if (choice == 12)
			chance(dg); 
		else 
			yahtzeeScore(dg); 
		return true;
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to scorge
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int sum = 0;
		for (int a = 0; a < 5; a++){
			if (dg.getDie(a).getLastRollValue() == choice)
				sum += choice; 
		}
		scores[choice - 1] = sum;
	}
	
	/**
	 *	Updates the scorecard for the Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		boolean valid = false;
		for (int a = 0; a < 6; a++){
			if (scoreCount[a] >= 3)
				valid = true;
		}
		if (valid)
			scores[6] = dg.getTotal();
		else
			scores[6] = 0; 
	}
	
	/**
	 *	Updates the scorecard for the Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fourOfAKind(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		boolean valid = false;
		for (int a = 0; a < 6; a++){
			if (scoreCount[a] >= 4)
				valid = true;
		}
		if (valid)
			scores[7] = dg.getTotal();
		else
			scores[7] = 0; 
	}
	
	/**
	 *	Updates the scorecard for the Full House choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fullHouse(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 5; a++){
			for (int b = a + 1; b < 6; b++){
				if ((scoreCount[a] == 3 && scoreCount[b] == 2) || 
					(scoreCount[a] == 2 && scoreCount[b] == 3))
					scores[8] = 25;
			}
		}
		if (scores[8] == -1)
			scores[8] = 0; 
	}
	
	/**
	 *	Updates the scorecard for the Small Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void smallStraight(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		int rowCounter = 0; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 6; a++){
			if (scoreCount[a] > 0)
				rowCounter++; 
			else if (rowCounter < 4)
				rowCounter = 0; 
		}
		if (rowCounter >= 4)
			scores[9] = 30;
		else
			scores[9] = 0; 
	}	
	
	/**
	 *	Updates the scorecard for the Large Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void largeStraight(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		int rowCounter = 0; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 6; a++){
			if (scoreCount[a] > 0)
				rowCounter++; 
			else if (rowCounter < 5)
				rowCounter = 0; 
		}
		if (rowCounter >= 5)
			scores[10] = 40;
		else
			scores[10] = 0;
	}
	
	/**
	 *	Updates the scorecard for the Chance choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void chance(DiceGroup dg) {
		scores[11] = dg.getTotal(); 
	}
	
	/**
	 *	Updates the scorecard for the Yahtzee choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void yahtzeeScore(DiceGroup dg) {
		int[] scoreCount = new int[6]; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		boolean valid = false;
		for (int a = 0; a < 6; a++){
			if (scoreCount[a] == 5)
				valid = true;
		}
		if (valid)
			scores[12] = 50;
		else
			scores[12] = 0; 
	}

}
