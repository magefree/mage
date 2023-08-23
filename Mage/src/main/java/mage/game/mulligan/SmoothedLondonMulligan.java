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

public class SmoothedLondonMulligan extends Mulligan {

    private final Map<UUID, Integer> startingHandSizes = new HashMap<>();
    private final Map<UUID, Integer> openingHandSizes = new HashMap<>();

    public SmoothedLondonMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    SmoothedLondonMulligan(final SmoothedLondonMulligan mulligan) {
        super(mulligan);

        this.startingHandSizes.putAll(mulligan.startingHandSizes);
        this.openingHandSizes.putAll(mulligan.openingHandSizes);
    }

    @Override
    public void executeMulliganPhase(Game game, int startingHandSize) {
        /*
         * Based on the London Mulligan, but looks at two hands from the top of the deck. Draws one and shuffles
         * the other away based on a stochastic process which leans towards the average land ratio of the deck.
         */

        for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
            openingHandSizes.put(playerId, startingHandSize);
            startingHandSizes.put(playerId, startingHandSize);
        }

        super.executeMulliganPhase(game, startingHandSize);
    }

    @Override
    public int mulliganDownTo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans) {
                    deduction = 0;
                }
            } else {
                deduction = 0;
            }
        }
        return openingHandSizes.get(playerId) - deduction;
    }

    @Override
    public boolean canTakeMulligan(Game game, Player player) {
        return super.canTakeMulligan(game, player) && openingHandSizes.get(player.getId()) > 0;
    }

    @Override
    public void mulligan(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int numCards = startingHandSizes.get(player.getId());
        player.getLibrary().addAll(player.getHand().getCards(game), game);
        player.getHand().clear();
        player.shuffleLibrary(null, game);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans) {
                    deduction = 0;
                    usedFreeMulligans.put(player.getId(), used + 1);
                }
            } else {
                deduction = 0;
                usedFreeMulligans.put(player.getId(), 1);
            }
        }
        openingHandSizes.put(playerId, openingHandSizes.get(playerId) - deduction);
        int newHandSize = openingHandSizes.get(player.getId());
        if (deduction == 0) {
            game.fireInformEvent(player.getLogName() +
                    " mulligans for free.");
        } else {
            game.fireInformEvent(player.getLogName() +
                    " mulligans down to " +
                    newHandSize +
                    (newHandSize == 1 ? " card" : " cards"));
        }
        List<Card> library = player.getLibrary().getCards(game);
        if (library.size() >= numCards*2 && numCards > 1) {
            double land_ratio = (double) library.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count() / (double) library.size();
            Set<Card> hand1 = player.getLibrary().getTopCards(game, numCards);
            Set<Card> hand2 = player.getLibrary().getTopCards(game, numCards * 2);
            hand2.removeAll(hand1);
            double hand1_ratio = (double) (hand1.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count()) / (double) numCards;
            double hand1_distance = Math.abs(land_ratio - hand1_ratio)+RandomUtil.nextDouble()*0.4;
            double hand2_ratio = (double) (hand2.stream().filter(w -> w.getCardType().contains(CardType.LAND)).count()) / (double) numCards;
            double hand2_distance = Math.abs(land_ratio - hand2_ratio)+RandomUtil.nextDouble()*0.4;
            double crossover_point = hand1_distance / (hand1_distance + hand2_distance);
            //game.debugMessage("1: "+hand1_ratio+", 2 = "+hand2_ratio+", expected = "+land_ratio);
            //game.debugMessage("hand1: "+hand1_distance+", hand2: "+hand2_distance+", point: "+crossover_point);
            if (crossover_point < 0.5) {
                player.drawCards(numCards, null, game);
            } else {
                player.putCardsOnBottomOfLibrary(new CardsImpl(hand1), game, null, false);
                player.drawCards(numCards, null, game);
            }
            player.shuffleLibrary(null, game);
        } else { //not enough cards in library or hand, just do a normal draw instead
            player.drawCards(numCards, null, game);
        }

        while (player.canRespond() && player.getHand().size() > newHandSize) {
            Target target = new TargetCardInHand(new FilterCard("card (" + (player.getHand().size() - newHandSize) + " more) to put on the bottom of your library"));
            player.chooseTarget(Outcome.Discard, target, null, game);
            player.putCardsOnBottomOfLibrary(new CardsImpl(target.getTargets()), game, null, true);
        }
    }

    @Override
    public void endMulligan(Game game, UUID playerId) {
    }

    @Override
    public SmoothedLondonMulligan copy() {
        return new SmoothedLondonMulligan(this);
    }
}
