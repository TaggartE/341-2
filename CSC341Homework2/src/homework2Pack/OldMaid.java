package homework2Pack;

import homework2Pack.IOldMaid.OldMaidI;

import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class OldMaid {
	
	public static void main(String[] args) {
		GameDriver game = new GameDriver();
		game.run();
	}

}


class GameDriver implements OldMaidI {
	
	public GameDriver() {
		init();
	}
	
	Deck deck;
	int hands;
	Hand[] hand;

	@Override
	public void init() {
		deck = new Deck(false);
		hands = Integer.parseInt(JOptionPane.showInputDialog("How many Players?"));
		hand=new Hand[hands];
		for(int i = 0; i<hand.length;i++) {
			hand[i]=new Hand();//as many hands as players
		}
		deck.shuffle();//shuffle the deck before distributing
		distribute();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int index=0;
		System.out.println("Game Starting!");
		while(hand.length>1) {
			
			for(int i = 0; i<hand.length;i++) {
				
				hand[i].sortByValue();
				
				if(hand[i].getCardCount()==0) {
					hand=eliminate(hand,i);
					break;
				}
				
				boolean removed=pairTest(hand[i]);
				if(!removed) {
					if(i-1==-1) {
						index=hand.length-1;
					}
					else {
						index=i;
					}
					System.out.println("Stealing Card!");
					hand[i].addCard(stealCard(hand[index]));
					pairTest(hand[i]);
				}
				
			
			}
		}
		while(hand[0].getCardCount()>1) {
			hand[0].sortByValue();
			pairTest(hand[0]);
		}
		System.out.println("End of game");
		
	}

	@Override
	public boolean pairTest(Hand hand) {
		//base case
		if(hand.getCardCount()==1)return false;
		for(int i = 0;i < hand.getCardCount()-1;i++) {
			
			if(hand.getCard(i).getValue()==hand.getCard(i+1).getValue()) {
				//then we remove them and return true
				//we also broadcast that these cards were removed
				System.out.println(hand.getCard(i)+" and "+hand.getCard(i+1)+" were removed!");
				
				hand.removeCard(i);
				
				hand.removeCard(i);//remove i again because i got removed so we do not need the +1
				
				hand.sortByValue();
				return true;
			}
		}
		return false;
	}

	@Override
	public Card stealCard(Hand hand) {
		//steal a random card from hand-1 
		Random random= new Random();
		int number = random.nextInt(hand.getCardCount()-0)+0;
		Card toreturn = hand.getCard(number);
		hand.removeCard(number);
		return toreturn;
	}

	@Override
	public void distribute() {
		// TODO Auto-generated method stub
		int helpingHand =0;
		for(int i = 0; i<51; i++) {
			hand[helpingHand].addCard(deck.dealCard());
			helpingHand++;
			if(helpingHand>=hands)helpingHand=0;
			//System.out.println(deck.cardsLeft());
		}
		
		//now we sort by value
		for(int i = 0; i< hands; i++) {
			hand[i].sortByValue();
		}
		
	}

	@Override
	public Hand[] eliminate(Hand[] hands,int handIndex) {
		// TODO Auto-generated method stub
		for(int i = 0; i < hands.length; i++){
		      if(i == handIndex){
		    	  System.out.println("Computer "+i+" Removed");
		        //swap
		        hands[i]=hands[hands.length-1];
		      }
		    }
		Hand[] toreturn = new Hand[hands.length-1];

		for(int i = 0; i<toreturn.length;i++) {
			toreturn[i]=hands[i];
		}
		return toreturn;
		
	}
	
}
