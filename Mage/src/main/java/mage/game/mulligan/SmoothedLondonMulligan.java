package mage.game.mulligan;

import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.util.RandomUtil;

import java.util.*;

public class SmoothedLondonMulligan extends LondonMulligan {


    public SmoothedLondonMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    SmoothedLondonMulligan(final SmoothedLondonMulligan mulligan) {
        super(mulligan);
    }

    @Override
    protected void drawHand(int numCards, Player player, Game game){
        List<Card> library = player.getLibrary().getCards(game);
        if (library.size() >= numCards*2 && numCards > 1) {
            double land_ratio = (double) library.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count() / (double) library.size();
            Set<Card> hand1 = player.getLibrary().getTopCards(game, numCards);
            Set<Card> hand2 = player.getLibrary().getTopCards(game, numCards * 2);
            hand2.removeAll(hand1);
            double hand1_ratio = (double) (hand1.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count()) / (double) numCards;
            double hand2_ratio = (double) (hand2.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count()) / (double) numCards;
            //distance = max(0,abs(land_ratio-hand_ratio)-0.15)+random()*0.3
            //Where land_ratio is (deck lands/deck size) and hand_ratio is (hand lands/hand size)
            //Keeps whichever hand's distance is smaller. Note that a 1-land difference is 1/7 = 0.143
            //So -0.15 means that there's no change in relative probabilities if within +1/-1 of the expected amount
            double hand1_distance = Math.max(0,Math.abs(land_ratio - hand1_ratio)-0.15)+RandomUtil.nextDouble()*0.3;
            double hand2_distance = Math.max(0,Math.abs(land_ratio - hand2_ratio)-0.15)+RandomUtil.nextDouble()*0.3;
            double crossover_point = hand1_distance / (hand1_distance + hand2_distance);
            //game.debugMessage("1: "+hand1_ratio+", 2 = "+hand2_ratio+", expected = "+land_ratio);
            //game.debugMessage("hand1: "+hand1_distance+", hand2: "+hand2_distance+", point: "+crossover_point);
            if (crossover_point < 0.5) {
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
