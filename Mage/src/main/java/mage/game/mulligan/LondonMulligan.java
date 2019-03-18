package mage.game.mulligan;

import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.*;

public class LondonMulligan extends Mulligan {

    protected Map<UUID, Integer> startingHandSizes = new HashMap<>();

    public LondonMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    @Override
    public void executeMulliganPhase(Game game, int startingHandSize) {
        for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
            startingHandSizes.put(playerId, startingHandSize);
        }

        super.executeMulliganPhase(game, startingHandSize);

        for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
            int handSize = startingHandSizes.get(playerId);
            Player player = game.getPlayer(playerId);
            if (player != null && player.getHand().size() > handSize) {
                int cardsToDiscard = player.getHand().size() - handSize;
                Cards cards = new CardsImpl();
                cards.addAll(player.getHand());
                TargetCard target = new TargetCard(cardsToDiscard, cardsToDiscard, Zone.HAND,
                        new FilterCard("cards to PUT on the BOTTOM of your library (Discard for Mulligan)"));
                player.chooseTarget(Outcome.Neutral, cards, target, null, game);
                player.putCardsOnBottomOfLibrary(new CardsImpl(target.getTargets()), game, null, true);
                cards.removeAll(target.getTargets());
                System.out.println(cardsToDiscard);
            }
        }
    }

    @Override
    public int mulliganDownTo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans) {
                    deduction = 0;
                }
            } else {
                deduction = 0;
            }
        }
        return startingHandSizes.get(playerId) - deduction;
    }

    @Override
    public void mulligan(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int numCards = player.getHand().size();
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
        startingHandSizes.put(playerId, startingHandSizes.get(playerId) - deduction);
        if (deduction == 0) {
            game.fireInformEvent(new StringBuilder(player.getLogName())
                    .append(" mulligans for free.")
                    .toString());
        } else {
            game.fireInformEvent(new StringBuilder(player.getLogName())
                    .append(" mulligans")
                    .append(" down to ")
                    .append((numCards - deduction))
                    .append(numCards - deduction == 1 ? " card" : " cards").toString());
        }
        player.drawCards(numCards, game);
    }

    @Override
    public void endMulligan(Game game, UUID playerId) {}

    @Override
    public LondonMulligan copy() {
        LondonMulligan mulligan = new LondonMulligan(getFreeMulligans());
        mulligan.startingHandSizes.putAll(startingHandSizes);
        return mulligan;
    }

}
