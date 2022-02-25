package homework2Pack;

public class IOldMaid {

	
	public interface OldMaidI{
		void init();
		//create deck
		//Java pop up for number of players
		void run();
		//run a pairTest per player 
		boolean pairTest(Hand hand);
		//true will remove the pair
		//false run will steal card only one time and then run again
		Card stealCard(Hand hand);
		void distribute();
		Hand[] eliminate(Hand[] hands,int handIndex);
		//hands out the cards to players
	}
}
