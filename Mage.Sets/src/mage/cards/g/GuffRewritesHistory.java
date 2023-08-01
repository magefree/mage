package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author Susucr
 */
public final class GuffRewritesHistory extends CardImpl {

    public GuffRewritesHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");


        // For each player, choose target nonenchantment, nonland permanent that player controls. Those permanents' owners shuffle them into their libraries. Each player who controlled one of those permanents exiles cards from the top of their library until they exile a nonland card, then puts the rest on the bottom of their library in a random order. Each player may cast the nonland card they exiled without paying its mana cost.
        this.getSpellAbility().addEffect(
                new GuffRewritesHistoryEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("For each player, choose target nonenchantment, nonland permanent that player controls. "
                                + "Those permanents' owners shuffle them into their libraries. Each player who controlled "
                                + "one of those permanents exiles cards from the top of their library until they exile a "
                                + "nonland card, then puts the rest on the bottom of their library in a random order. "
                                + "Each player may cast the nonland card they exiled without paying its mana cost.")
        );

        this.getSpellAbility().setTargetAdjuster(GuffRewritesHistoryAdjuster.instance);
    }

    private GuffRewritesHistory(final GuffRewritesHistory card) {
        super(card);
    }

    @Override
    public GuffRewritesHistory copy() {
        return new GuffRewritesHistory(this);
    }
}

enum GuffRewritesHistoryAdjuster implements TargetAdjuster {
    instance;
    private static final Predicate<MageObject> predicate =
            Predicates.and(
                    Predicates.not(CardType.ENCHANTMENT.getPredicate()),
                    Predicates.not(CardType.LAND.getPredicate())
            );

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterPermanent(
                    "nonenchantment, nonland permanent "
                            + (ability.isControlledBy(playerId) ? "you control" : "controlled by " + player.getName())
            );
            filter.add(predicate);
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(filter));
        }
    }
}

class GuffRewritesHistoryEffect extends OneShotEffect {

    public GuffRewritesHistoryEffect() {
        super(Outcome.Neutral);
    }

    private GuffRewritesHistoryEffect(final GuffRewritesHistoryEffect effect) {
        super(effect);
    }

    @Override
    public GuffRewritesHistoryEffect copy() {
        return new GuffRewritesHistoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // set of controllers for the second part of the effect.
        Set<UUID> controllers = new HashSet<>();
        Map<UUID, Cards> toShuffle = new HashMap<>();
        for (Target target : source.getTargets()) {
            for (UUID permId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permId);
                if (permanent == null) {
                    continue;
                }
                Player owner = game.getPlayer(permanent.getOwnerId());
                Player controller = game.getPlayer(permanent.getControllerId());

                if (controller != null) {
                    controllers.add(controller.getId());
                }

                if (owner != null) {
                    toShuffle.computeIfAbsent(owner.getId(), k -> new CardsImpl());
                    toShuffle.get(owner.getId())
                             .add(permanent);
                }
            }
        }

        // Only one shuffle per owner!
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (toShuffle.containsKey(playerId)) {
                Player owner = game.getPlayer(playerId);
                // Those permanents' owners shuffle them into their libraries.
                owner.shuffleCardsToLibrary(toShuffle.get(playerId), game, source);
            }
        }

        // Nonland card for each player for the third part of the ability.
        Map<UUID, Card> nonlands = new HashMap();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!controllers.contains(playerId)) {
                continue;
            }

            Player controller = game.getPlayer(playerId);
            if (controller == null) {
                continue;
            }

            Cards cards = new CardsImpl();
            Card nonland = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                cards.add(card);
                if (!card.isLand(game)) {
                    nonland = card;
                    break;
                }
            }

            // Each player who controlled one of those permanents exiles cards from the top of their library
            // until they exile a nonland card, then puts the rest on the bottom of their library in a random order.
            controller.moveCards(cards, Zone.EXILED, source, game);
            cards.retainZone(Zone.EXILED, game);
            // reveal all the exiled cards, as they are reshuffled instantly and do not stay in exile.
            controller.revealCards(source, " — " + controller.getName(), cards, game);
            if (nonland != null && cards.contains(nonland)) {
                nonlands.put(playerId, nonland);
                cards.remove(nonland);
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!nonlands.containsKey(playerId)) {
                continue;
            }

            Player player = game.getPlayer(playerId);
            Card card = nonlands.get(playerId);
            if (player == null || card == null) {
                continue;
            }

            // Each player may cast the nonland card they exiled without paying its mana cost.
            CardUtil.castSpellWithAttributesForFree(
                    player, source, game, card
            );
        }

        return true;
    }

}