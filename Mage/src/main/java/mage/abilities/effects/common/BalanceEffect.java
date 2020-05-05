package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author emerald000
 */
public class BalanceEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands to keep");
    private static final FilterControlledPermanent filter2 = new FilterControlledCreaturePermanent("creatures to keep");
    private static final FilterCard filter3 = new FilterCard("cards to keep");

    public BalanceEffect() {
        super(Outcome.Sacrifice);
        staticText = "each player chooses a number of lands they control "
                + "equal to the number of lands controlled by the player "
                + "who controls the fewest, then sacrifices the rest. "
                + "Players discard cards and sacrifice creatures the same way";
    }

    private BalanceEffect(final BalanceEffect effect) {
        super(effect);
    }

    @Override
    public BalanceEffect copy() {
        return new BalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        //Lands
        int minLand = Integer.MAX_VALUE;
        Cards landsToSacrifice = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int count = game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, player.getId(), game);
            if (count < minLand) {
                minLand = count;
            }
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetControlledPermanent target = new TargetControlledPermanent(minLand, minLand, filter, true);
            if (!target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                continue;
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, player.getId(), source.getSourceId(), game)) {
                if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                    landsToSacrifice.add(permanent);
                }
            }
        }

        for (UUID cardId : landsToSacrifice) {
            Permanent permanent = game.getPermanent(cardId);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }

        //Creatures
        int minCreature = Integer.MAX_VALUE;
        Cards creaturesToSacrifice = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
            }
            int count = game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, player.getId(), game);
            if (count < minCreature) {
                minCreature = count;
            }
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, filter2, true);
            if (!target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                continue;
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source.getSourceId(), game)) {
                if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                    creaturesToSacrifice.add(permanent);
                }
            }
        }

        for (UUID cardId : creaturesToSacrifice) {
            Permanent permanent = game.getPermanent(cardId);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }

        //Cards in hand
        int minCard = Integer.MAX_VALUE;
        Map<UUID, Cards> cardsToDiscard = new HashMap<>(2);
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
            }
            int count = player.getHand().size();
            if (count < minCard) {
                minCard = count;
            }
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl();
            TargetCardInHand target = new TargetCardInHand(minCard, filter3);
            if (!target.choose(Outcome.Discard, player.getId(), source.getSourceId(), game)) {
                continue;
            }
            for (Card card : player.getHand().getCards(game)) {
                if (card != null && !target.getTargets().contains(card.getId())) {
                    cards.add(card);
                }
            }
            cardsToDiscard.put(playerId, cards);
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && cardsToDiscard.get(playerId) != null) {
                player.discard(cardsToDiscard.get(playerId), source, game);
            }
        }
        return true;
    }
}
