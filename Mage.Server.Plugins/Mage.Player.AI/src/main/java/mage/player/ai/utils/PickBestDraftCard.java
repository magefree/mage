package mage.player.ai.utils;

import mage.cards.Card;
import org.apache.log4j.Logger;

import mage.player.ai.utils.CardDatabase;
import mage.cards.decks.Deck;

import java.io.InputStream;
import java.util.*;
import mage.player.ai.ComputerPlayer;

/**
 * Class responsible for reading ratings from resources and rating gived cards.
 * Based on card relative ratings from resources and card parameters.
 *
 * @author nantuko
 */
public class PickBestDraftCard {

    private static Map<String, CardDatabase> ratings;
    
    private static final Logger log = Logger.getLogger(PickBestDraftCard.class);
    /**
     * Rating that is given for new cards.
     * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
     */
    private static final int DEFAULT_NOT_RATED_CARD_RATING = 4;



    /**
     * Hide constructor.
     */
    private PickBestDraftCard() {
    }

    /**
     * Get absolute score of the card.
     * Depends on type, manacost, rating.
     * If allowedColors is null then the rating is retrieved from the cache
     *
     * @param card
     * @return
     */
    public static Card pickBestDraftCard(List<Card> cards, Deck deck) {
        if (cards.isEmpty()) {
            return null;
        }
        Card bestCard = null;
        int bestCardScore = 0;
        String bestCardType = "";
        
        int cardScore;
        String cardType;
        
        int prevDeckScore;
        int postDeckScore;
        
        int bestChange = 0;
        
        int[] prevScore = new int[10];
        int[] postScore = new int[10];
        
        /*
         * Previous best deck score with 2 colors.
         */
        prevScore[0] = deck.draftBG;
        prevScore[1] = deck.draftBR;
        prevScore[2] = deck.draftBU;
        prevScore[3] = deck.draftBW;
        prevScore[4] = deck.draftGR;
        prevScore[5] = deck.draftGU;
        prevScore[6] = deck.draftGW;
        prevScore[7] = deck.draftRU;
        prevScore[8] = deck.draftRW;
        prevScore[9] = deck.draftUW;
        
        Arrays.sort(prevScore);
        
        prevDeckScore = prevScore[9];
        
        
        for (Card card : cards) {
            
            if (ratings.get(card.getName()) == null) {
                cardScore = 30;
                cardType = "NA";
            } else {
                cardScore = ratings.get(card.getName()).cardDraftScore;
                cardType = ratings.get(card.getName()).cardDraftType;
            }
            
            if (cardType.equals("B")||
                cardType.equals("G")||
                cardType.equals("BG")||
                cardType.equals("NA"))
                postScore[0] = deck.draftBG + cardScore;
            else postScore[0] = deck.draftBG;

            if (cardType.equals("B")||
                cardType.equals("R")||
                cardType.equals("BR")||
                cardType.equals("NA"))
                postScore[1] = deck.draftBR + cardScore;
            else postScore[1] = deck.draftBR;

            if (cardType.equals("B")||
                cardType.equals("U")||
                cardType.equals("BU")||
                cardType.equals("NA"))
                postScore[2] = deck.draftBU + cardScore;
            else postScore[2] = deck.draftBU;

            if (cardType.equals("B")||
                cardType.equals("W")||
                cardType.equals("BW")||
                cardType.equals("NA"))
                postScore[3] = deck.draftBW + cardScore;
            else postScore[3] = deck.draftBW;

            if (cardType.equals("G")||
                cardType.equals("R")||
                cardType.equals("GR")||
                cardType.equals("NA"))
                postScore[4] = deck.draftGR + cardScore;
            else postScore[4] = deck.draftGR;

            if (cardType.equals("G")||
                cardType.equals("U")||
                cardType.equals("GU")||
                cardType.equals("NA"))
                postScore[5] = deck.draftGU + cardScore;
            else postScore[5] = deck.draftGU;

            if (cardType.equals("G")||
                cardType.equals("W")||
                cardType.equals("GW")||
                cardType.equals("NA"))
                postScore[6] = deck.draftGW + cardScore;
            else postScore[6] = deck.draftGW;

            if (cardType.equals("R")||
                cardType.equals("U")||
                cardType.equals("RU")||
                cardType.equals("NA"))
                postScore[7] = deck.draftRU + cardScore;
            else postScore[7] = deck.draftRU;

            if (cardType.equals("R")||
                cardType.equals("W")||
                cardType.equals("RW")||
                cardType.equals("NA"))
                postScore[8] = deck.draftRW + cardScore;
            else postScore[8] = deck.draftRW;

            if (cardType.equals("U")||
                cardType.equals("W")||
                cardType.equals("UW")||
                cardType.equals("NA"))
                postScore[9] = deck.draftUW + cardScore;
            else postScore[9] = deck.draftUW;
            
            Arrays.sort(postScore);
            
            postDeckScore = postScore[9];
            
            if ((postDeckScore - prevDeckScore) >= bestChange) {
                bestChange = postDeckScore - prevDeckScore;
                bestCard = card;
                bestCardScore = cardScore;
                bestCardType = cardType;
            }
        }
        
        /*
         * Counterpick
         */
        if (bestChange <= 30) {
            bestCardScore = 0;
            for (Card card : cards) {
                cardScore = ratings.get(card.getName()).cardDraftScore;
                cardType = ratings.get(card.getName()).cardDraftType;
                
                if (cardScore >= bestCardScore) {
                    bestCard = card;
                    bestCardScore = cardScore;
                    bestCardType = cardType;
                }
            }    
        }
    if (bestCardType.equals("B")||
        bestCardType.equals("G")||
        bestCardType.equals("BG")||
        bestCardType.equals("NA"))
        deck.draftBG = deck.draftBG + bestCardScore;

    if (bestCardType.equals("B")||
        bestCardType.equals("R")||
        bestCardType.equals("BR")||
        bestCardType.equals("NA"))
        deck.draftBR = deck.draftBR + bestCardScore;

    if (bestCardType.equals("B")||
        bestCardType.equals("U")||
        bestCardType.equals("BU")||
        bestCardType.equals("NA"))
        deck.draftBU = deck.draftBU + bestCardScore;

    if (bestCardType.equals("B")||
        bestCardType.equals("W")||
        bestCardType.equals("BW")||
        bestCardType.equals("NA"))
        deck.draftBW = deck.draftBW + bestCardScore;

    if (bestCardType.equals("G")||
        bestCardType.equals("R")||
        bestCardType.equals("GR")||
        bestCardType.equals("NA"))
        deck.draftGR = deck.draftGR + bestCardScore;

    if (bestCardType.equals("G")||
        bestCardType.equals("U")||
        bestCardType.equals("GU")||
        bestCardType.equals("NA"))
        deck.draftGU = deck.draftGU + bestCardScore;

    if (bestCardType.equals("G")||
        bestCardType.equals("W")||
        bestCardType.equals("GW")||
        bestCardType.equals("NA"))
        deck.draftGW = deck.draftGW + bestCardScore;

    if (bestCardType.equals("R")||
        bestCardType.equals("U")||
        bestCardType.equals("RU")||
        bestCardType.equals("NA"))
        deck.draftRU = deck.draftRU + bestCardScore;

    if (bestCardType.equals("R")||
        bestCardType.equals("W")||
        bestCardType.equals("RW")||
        bestCardType.equals("NA"))
        deck.draftRW = deck.draftRW + bestCardScore;

    if (bestCardType.equals("U")||
        bestCardType.equals("W")||
        bestCardType.equals("UW")||
        bestCardType.equals("NA"))
        deck.draftUW = deck.draftUW + bestCardScore;
        
        log.info(bestCard.getName() + "   " + bestCardScore + "   " + bestCardType);
        return bestCard;
    }


    /**
     * Reads ratings from resources.
     */
    public synchronized static void readRatings() {
        if (ratings == null) {
            ratings = new HashMap<String, CardDatabase>();
			readFromFile("/ratings.csv");
        }
    }
    
    private static void readFromFile(String path) {
        try {
            InputStream is = RateCard.class.getResourceAsStream(path);
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] s = line.split(":");
                if (s.length == 3) {
                    Integer rating = Integer.parseInt(s[2].trim());
                    String type = s[1].trim();
                    String name = s[0].trim();
                    ratings.put(name, new CardDatabase(type, rating));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ratings.clear(); // no rating available on exception
        }
    }
}
