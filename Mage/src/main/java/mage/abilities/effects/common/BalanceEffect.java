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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public class BalanceEffect extends OneShotEffect {

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

        choosePermanentsToKeep(game, source, controller, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, new FilterControlledLandPermanent("lands to keep"));
        choosePermanentsToKeep(game, source, controller, StaticFilters.FILTER_CONTROLLED_CREATURE, new FilterControlledCreaturePermanent("creatures to keep"));

        //Cards in hand
        int lowestHandSize = Integer.MAX_VALUE;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            lowestHandSize = Math.min(lowestHandSize, player.getHand().size());
        }

        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            TargetCardInHand target = new TargetCardInHand(lowestHandSize, new FilterCard("cards to keep"));
            if (!target.choose(Outcome.Protect, player.getId(), source.getSourceId(), game)) {
                continue;
            }

            Set<Card> allCardsInHand = player.getHand().getCards(game);
            Set<Card> cardsToKeep = new LinkedHashSet<>();

            for (Card card : allCardsInHand) {
                if (card != null && target.getTargets().contains(card.getId())) {
                    cardsToKeep.add(card);
                }
            }

            cardsToDiscard.put(playerId, allCardsInHand.stream()
                .filter(e -> !cardsToKeep.contains(e))
                .collect(CardsImpl::new, CardsImpl::add, CardsImpl::addAll));
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && cardsToDiscard.get(playerId) != null) {
                player.discard(cardsToDiscard.get(playerId), false, source, game);
            }
        }

        return true;
    }

    private void choosePermanentsToKeep(Game game, Ability source, Player controller,
                                         FilterControlledPermanent filterPermanent, FilterControlledPermanent filterPermanentDialog) {
        int lowestPermanentsCount = Integer.MAX_VALUE;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            lowestPermanentsCount = Math.min(lowestPermanentsCount,
                    game.getBattlefield().countAll(filterPermanent, player.getId(), game));
        }

        List<Permanent> permanentsToSacrifice = new ArrayList<>();

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            TargetControlledPermanent target = new TargetControlledPermanent(lowestPermanentsCount, lowestPermanentsCount, filterPermanentDialog, true);
            if (!target.choose(Outcome.Protect, player.getId(), source.getSourceId(), game)) {
                continue;
            }

            List<Permanent> allPermanentsOfType = game.getBattlefield().getActivePermanents(filterPermanent, player.getId(), source.getSourceId(), game);
            List<Permanent> permanentsToKeep = new ArrayList<>();

            for (Permanent permanent : allPermanentsOfType) {
                if (permanent != null && target.getTargets().contains(permanent.getId())) {
                    permanentsToKeep.add(permanent);
                }
            }

            List<Permanent> playerPermanentsToSacrifice = allPermanentsOfType.stream().filter(e -> !permanentsToKeep.contains(e)).collect(Collectors.toList());
            permanentsToSacrifice.addAll(playerPermanentsToSacrifice);

            if (playerPermanentsToSacrifice.isEmpty()) {
                game.informPlayers(player.getLogName() + " chose permanents to be sacrificed: "
                        + playerPermanentsToSacrifice.stream().map(Permanent::getLogName).collect(Collectors.joining(", ")));
            }
        }

        for (Permanent permanent : permanentsToSacrifice) {
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
    }
}
