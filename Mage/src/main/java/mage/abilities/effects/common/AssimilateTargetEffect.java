package mage.abilities.effects.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssimilateContinuousEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author muz
 */
public class AssimilateTargetEffect extends OneShotEffect {

     public AssimilateTargetEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    protected AssimilateTargetEffect(final AssimilateTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssimilateTargetEffect copy() {
        return new AssimilateTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Counters counters = new Counters();
        counters.addCounter(CounterType.P1P1.createInstance());

        Set<Card> cardsToMove = new HashSet<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(targetId);
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                cardsToMove.add(card);
                game.setEnterWithCounters(targetId, counters.copy());

                // Apply the continuous effect before moving the card to have the proper types on entering.
                AssimilateContinuousEffect continuousEffect = new AssimilateContinuousEffect(
                    new MageObjectReference(card, game, 1)
                );
                game.addEffect(continuousEffect, source);
            }
        }
        controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, false, false, false, null);

        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("assimilate ");
        String targetName = mode.getTargets().isEmpty()
                ? "target"
                : mode.getTargets().get(0).getTargetName();
        sb.append(targetName);
        sb.append(". <i>(Put it onto the battlefield under your control with a +1/+1 counter. "
            + "It's a Borg artifact creature and loses all other creature types.)</i>");
        return sb.toString();
    }
}
