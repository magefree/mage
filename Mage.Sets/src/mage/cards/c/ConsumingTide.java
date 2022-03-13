package mage.cards.c;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class ConsumingTide extends CardImpl {

    public ConsumingTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Each player chooses a nonland permanent they control. Return all nonland permanents not chosen this way to their owners' hands.
        // Then you draw a card for each opponent who has more cards in their hand than you.
        this.getSpellAbility().addEffect(new ConsumingTideEffect());
    }

    private ConsumingTide(final ConsumingTide card) {
        super(card);
    }

    @Override
    public ConsumingTide copy() {
        return new ConsumingTide(this);
    }
}

class ConsumingTideEffect extends OneShotEffect {

    public ConsumingTideEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Each player chooses a nonland permanent they control. Return all nonland permanents not chosen this way to their owners' hands. "
                + "Then you draw a card for each opponent who has more cards in their hand than you";
    }

    private ConsumingTideEffect(final ConsumingTideEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingTideEffect copy() {
        return new ConsumingTideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        HashSet<UUID> chosenPermanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetNonlandPermanent target = new TargetNonlandPermanent();
                target.setNotTarget(true);
                player.choose(Outcome.Benefit, target, source, game);
                UUID permId = target.getFirstTarget();
                if (permId != null) {
                    chosenPermanents.add(permId);
                }
            }
        }
        HashSet<Card> permanentsToHand = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_NON_LAND, controller.getId(), source, game)) {
            if (!chosenPermanents.contains(permanent.getId())) {
                permanentsToHand.add(permanent);
            }
        }
        controller.moveCards(permanentsToHand, Zone.HAND, source, game);
        int controllerHandSize = controller.getHand().size();
        int cardsToDraw = 0;
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.getHand().size() > controllerHandSize) {
                cardsToDraw++;
            }
        }
        controller.drawCards(cardsToDraw, source, game);
        return true;
    }
}
