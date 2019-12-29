package mage.game.mulligan;

import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LondonMulligan extends Mulligan {

    protected Map<UUID, Integer> startingHandSizes = new HashMap<>();
    protected Map<UUID, Integer> openingHandSizes = new HashMap<>();

    public LondonMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    @Override
    public void executeMulliganPhase(Game game, int startingHandSize) {
        /*
         * 103.4. Each player draws a number of cards equal to their starting hand size, which is normally
         * seven. (Some effects can modify a player’s starting hand size.) A player who is dissatisfied with
         * their initial hand may take a mulligan. First, the starting player declares whether they will
         * take a mulligan. Then each other player in turn order does the same. Once each player has made a
         * declaration, all players who decided to take mulligans do so at the same time. To take a mulligan,
         * a player shuffles the cards in their hand back into their library, draws a new hand of cards equal
         * to their starting hand size, then puts a number of those cards onto the bottom of their library in
         * any order equal to the number of times that player has taken a mulligan. Once a player chooses not
         * to take a mulligan, the remaining cards become the player’s opening hand, and that player may not
         * take any further mulligans. This process is then repeated until no player takes a mulligan. A
         * player can’t take a number of mulligans greater their starting hand size.
         *
         * https://magic.wizards.com/en/articles/archive/competitive-gaming/mythic-championship-ii-format-and-london-test-2019-02-21
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
            game.fireInformEvent(new StringBuilder(player.getLogName())
                    .append(" mulligans for free.")
                    .toString());
        } else {
            game.fireInformEvent(new StringBuilder(player.getLogName())
                    .append(" mulligans")
                    .append(" down to ")
                    .append(newHandSize)
                    .append(newHandSize == 1 ? " card" : " cards").toString());
        }
        player.drawCards(numCards, game);

        if (player.getHand().size() > newHandSize) {
            int cardsToDiscard = player.getHand().size() - newHandSize;
            Cards cards = new CardsImpl();
            cards.addAll(player.getHand());
            TargetCard target = new TargetCard(cardsToDiscard, cardsToDiscard, Zone.HAND,
                    new FilterCard("card" + (cardsToDiscard > 1 ? "s" : "") + " to PUT on the BOTTOM of your library (Discard for Mulligan)"));
            player.chooseTarget(Outcome.Neutral, cards, target, null, game);
            player.putCardsOnBottomOfLibrary(new CardsImpl(target.getTargets()), game, null, true);
            cards.removeAll(target.getTargets());
        }
    }

    @Override
    public void endMulligan(Game game, UUID playerId) {
    }

    @Override
    public LondonMulligan copy() {
        LondonMulligan mulligan = new LondonMulligan(getFreeMulligans());
        mulligan.openingHandSizes.putAll(openingHandSizes);
        mulligan.startingHandSizes.putAll(startingHandSizes);
        return mulligan;
    }

}
