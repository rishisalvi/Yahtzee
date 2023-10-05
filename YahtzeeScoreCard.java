/**
 *	Describe the scorecard here.
 *
 *	@author	Rishi Salvi
 *	@since	10/03/23
 */
public class YahtzeeScoreCard {
	private int[] scores; 
	
	public YahtzeeScoreCard(){
		scores = new int[13]; 
		for (int i = 0; i < 13; i++)
			scores[i] == -1; 
	}
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(int i) {
		return scores[i];
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
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) {
		for (int a = 0; a < 3; a++){
			for (int b = a + 1; b < 4; b++){
				for (int c = b + 1; c < 5; c++){
					if (dg.getDie(a).getLastRollValue() ==
						dg.getDie(b).getLastRollValue() ==
						dg.getDie(c).getLastRollValue())
						scores[6] = dg.getTotal();
					else
						scores[6] = 0; 
				}
			}
		}
	}
	
	public void fourOfAKind(DiceGroup dg) {
		for (int a = 0; a < 2; a++){
			for (int b = a + 1; b < 3; b++){
				for (int c = b + 1; c < 4; c++){
					for (int d = c + 1; d < 5; d++){
						if (dg.getDie(a).getLastRollValue() ==
							dg.getDie(b).getLastRollValue() ==
							dg.getDie(c).getLastRollValue() ==
							dg.getDie(d).getLastRollValue())
							scores[7] = dg.getTotal();
						else
							scores[7] = 0; 
					}
				}
			}
		}
	}
	
	public void fullHouse(DiceGroup dg) {
		int[] scoreCount = ""; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 4; a++){
			for (int b = a + 1; b < 5; b++){
				if ((scoreCount[a] == 3 && scoreCount[b] == 2) || 
					(scoreCount[a] == 2 && scoreCount[b] == 3))
					scores[8] = 25;
				else
					scores[8] = 0;
			}
		}
		
	}
	
	public void smallStraight(DiceGroup dg) {
		int[] scoreCount = ""; 
		int rowCounter = 0; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 5; a++){
			if (scoreCount[a] > 0)
				rowCounter++; 
			else
				rowCounter = 0; 
		}
		if (rowCounter >= 4)
			scores[9] = 30;
		else
			scores[9] = 0; 
	}	
	
	public void largeStraight(DiceGroup dg) {
		int[] scoreCount = ""; 
		int rowCounter = 0; 
		for (int i = 0; i < 5; i++)
			scoreCount[dg.getDie(i).getLastRollValue() - 1]++;
		for (int a = 0; a < 5; a++){
			if (scoreCount[a] > 0)
				rowCounter++; 
			else
				rowCounter = 0; 
		}
		if (rowCounter >= 5)
			scores[10] = 40;
		else
			scores[10] = 0;
	}
	
	public void chance(DiceGroup dg) {
		scores[11] = dg.getTotal(); 
	}
	
	public void yahtzeeScore(DiceGroup dg) {
		if (dg.getDie(0).getLastRollValue() == 
			dg.getDie(1).getLastRollValue() == 
			dg.getDie(2).getLastRollValue() == 
			dg.getDie(3).getLastRollValue() == 
			dg.getDie(4).getLastRollValue())
			scores[12] = 50; 
		else
			scores[12] = 0; 
	}

}
