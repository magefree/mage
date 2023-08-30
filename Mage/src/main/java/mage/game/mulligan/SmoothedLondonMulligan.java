package mage.game.mulligan;

import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacedCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.*;

public class SmoothedLondonMulligan extends LondonMulligan {

    public SmoothedLondonMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    SmoothedLondonMulligan(final SmoothedLondonMulligan mulligan) {
        super(mulligan);
    }

    private static double countLands(Collection<Card> cards, boolean library){
        double land_count = 0;
        for (Card card : cards){
            if (card.isLand()) {
                land_count += 1;
            } else if (card instanceof ModalDoubleFacedCard && ((ModalDoubleFacedCard)card).getRightHalfCard().isLand()){
                if (library) { //count MDFCs with a nonland front and a land back as:
                    land_count += 0.5;//half a land in a library
                } else if (RandomUtil.nextBoolean()){
                    land_count += 1; //randomly as a land or nonland in a hand
                    // This avoids the bias problem where adjusting the deck land ratio to be (integer vs X.5)/7
                    // can greatly affect the chance of drawing an MDFC
                }
            }
        }
        return land_count;
    }
    @Override
    public void drawHand(int numCards, Player player, Game game){
        List<Card> library = player.getLibrary().getCards(game);
        if (library.size() >= numCards*2 && numCards > 1) {
            double land_ratio = countLands(library, true) / (double) library.size();
            Set<Card> hand1 = player.getLibrary().getTopCards(game, numCards);
            Set<Card> hand2 = player.getLibrary().getTopCards(game, numCards * 2);
            hand2.removeAll(hand1);
            double hand1_ratio = countLands(hand1, false) / (double) numCards;
            double hand2_ratio = countLands(hand2, false) / (double) numCards;
            //distance = max(0,abs(land_ratio-hand_ratio)-0.15)+random()*0.3
            //Where land_ratio is (deck lands/deck size) and hand_ratio is (hand lands/hand size)
            //Keeps whichever hand's distance is smaller. Note that a 1-land difference is 1/7 = 0.143
            //So -0.15 means that there's no change in relative probabilities if within +1/-1 of the expected amount
            double hand1_distance = Math.max(0,Math.abs(land_ratio - hand1_ratio)-0.15)+RandomUtil.nextDouble()*0.3;
            double hand2_distance = Math.max(0,Math.abs(land_ratio - hand2_ratio)-0.15)+RandomUtil.nextDouble()*0.3;
            //game.debugMessage("1: "+hand1_ratio+", 2 = "+hand2_ratio+", expected = "+land_ratio);
            //game.debugMessage("hand1: "+hand1_distance+", hand2: "+hand2_distance);
            if (hand1_distance < hand2_distance) {
                player.drawCards(numCards, null, game);
                player.putCardsOnBottomOfLibrary(new CardsImpl(hand2), game, null, false);
                //These are immediately shuffled away, but needed for consistent testing
            } else {
                player.putCardsOnBottomOfLibrary(new CardsImpl(hand1), game, null, false);
                player.drawCards(numCards, null, game);
            }
            player.shuffleLibrary(null, game);
        } else { //not enough cards in library or hand, just do a normal draw instead
            player.drawCards(numCards, null, game);
        }
    }

    @Override
    public SmoothedLondonMulligan copy() {
        return new SmoothedLondonMulligan(this);
    }
}
