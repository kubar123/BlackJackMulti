/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackmulti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jakub Rybicki
 */
public class Dealer {
    //--------card value settings-----------//
    private static final int ACE_VALUE=11;
    private static final int FACE_VALUE=10;
    //------------------------------//
    
    //---------Server info -------------------//
    String serverHostname = new String ("127.0.0.1");
    
    //-------------------------------//
    
    
    //the dealers current hand
    private static ArrayList<String> mDealerHand;
    //hand's points
    private static int mDealerPoints;
    
    //the list of cards
    // ---------- now assigned from server
    private static String mDeck[];
//    ={
//        "♠ 2", "♠ 3", "♠ 4", "♠ 5", "♠ 6", "♠ 7", "♠ 8", "♠ 9", "♠ 10",
//        "♠ K", "♠ Q", "♠ J", "♠ ACE", "♥ 2", "♥ 3", "♥ 4", "♥ 5", "♥ 6",
//        "♥ 7", "♥ 8", "♥ 9", "♥ 10", "♥ K", "♥ Q", "♥ J", "♥ ACE", "♦ 2",
//        "♦ 3", "♦ 4", "♦ 5", "♦ 6", "♦ 7", "♦ 8", "♦ 9", "♦ 10", "♦ K", 
//        "♦ Q", "♦ J", "♦ ACE","♣ 2", "♣ 3", "♣ 4", "♣ 5", "♣ 6", "♣ 7", 
//        "♣ 8", "♣ 9", "♣ 10", "♣ K", "♣ Q", "♣ J", "♣ ACE"
//    };

    //deck that we will be playing with
    public static ArrayList<String> mGameDeck=new ArrayList<>();
    

    
    //removes one card from the deck, returns it and its value
    //[0] = card    [1] = card value
    protected static String[] takeCardFromDeck(){
        String cardAndValue[]={"",""};
        int chosenCard = (int) (Math.random() * mGameDeck.size());
        //picks a random card from the deck
       // for(String card: mGameDeck) if (--chosenCard < 0){
        String card=mGameDeck.get(chosenCard);
        mGameDeck.remove(card);//remove the card from the deck
        cardAndValue[0]=card; // add the card and card's value 
        cardAndValue[1]=""+getCardValue(card);
        return cardAndValue;    
        //}
        
        //out of cards - should never happen
      //  System.err.println("ERROR - out of cards");
     //   System.exit(1);
      //  return null;
    } 
    
    // Dealer makes his moves
    protected static void makeMove(){
        do{
            String[] selectedCard=takeCardFromDeck();
            //add card to hand and add points
            mDealerHand.add(selectedCard[0]);
            mDealerPoints+=Integer.parseInt(selectedCard[1]);
        }while(mDealerPoints<17);
    }
    // ****************
    
    
    
    //get the value of one card
    protected static int getCardValue(String card) {
        int cardValue=0;       
        String selected=card.substring(2);
    //try converting the card into a number - if cannot it is a FACE/ACE card
        try{
            cardValue+=Integer.parseInt(selected);
        }catch(NumberFormatException e){
            if(selected.equalsIgnoreCase("ACE")){
                cardValue+=ACE_VALUE;
            }else cardValue+=FACE_VALUE;
        }
            
    
        return cardValue;
    }
    
    //Used to get the value of a full hand of cards
    private static int getHandValue() {
        int cardValue = 0;
        for (String card : mDealerHand) {
            String selected = card.substring(2);

            //try converting the card into a number - if cannot it is a KQJ/ACE
            try {
                cardValue += Integer.parseInt(selected);
            } catch (NumberFormatException e) {
                if (selected.equalsIgnoreCase("ACE")) {
                    cardValue += ACE_VALUE;
                } else {
                    cardValue += FACE_VALUE;
                }
            }

        }
        return cardValue;
    }
    
    
    private static void assignDeck(String[] deck){
        mDeck=deck;
      //  mGameDeck=new HashSet<>(Arrays.asList(deck));
    }
}
