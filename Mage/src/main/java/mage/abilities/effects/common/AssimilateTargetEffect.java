package mage.abilities.effects.common;

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
import mage.util.CardUtil;

import java.util.*;

/**
 * 701.72. Assimilate
 * <p>
 * 701.72a To assimilate a permanent, gain control of it and put a +1/+1 counter on it. It becomes
 * a Borg artifact creature.
 * <p>
 * 701.72b To assimilate a permanent card that’s in a zone other than the battlefield, put it onto
 * the battlefield under your control with a +1/+1 counter on it. It’s a Borg artifact creature.
 * <p>
 * 701.72c An object may have an ability printed on it that assimilates another object and another
 * ability that refers to permanents “assimilated with [this object].” These abilities are linked;
 * the second refers only to permanents that have been assimilated due to the first. See rule 607,
 * “Linked Abilities.”
 *
 * @author muz, JayDi85
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

        Set<Card> cardsToMove = new LinkedHashSet<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(targetId);
            if (card != null && game.getState().getZone(card.getId()) != Zone.BATTLEFIELD) {
                cardsToMove.add(card);
            }
        }
        return assimilateCards(controller, cardsToMove, source, game);
    }

    public static boolean assimilateCard(Player controller, Card card, Ability source, Game game) {
        if (controller == null || card == null) {
            return false;
        }
        return assimilateCards(controller, Collections.singleton(card), source, game);
    }

    public static boolean assimilateCards(Player controller, Set<Card> cardsToMove, Ability source, Game game) {
        if (controller == null || cardsToMove.isEmpty()) {
            return false;
        }

        Counters counters = new Counters();
        counters.addCounter(CounterType.P1P1.createInstance());

        for (Card card : cardsToMove) {
            Card enterCard = CardUtil.getDefaultCardSideForBattlefield(game, card);
            game.setEnterWithCounters(enterCard.getId(), counters.copy());

            // Apply the continuous effect before moving the card to have the proper types on entering.
            game.addEffect(new AssimilateContinuousEffect(new MageObjectReference(enterCard, game, 1)), source);
        }

        return controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, false, false, false, null);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "assimilate "
                + getTargetPointer().describeTargets(mode.getTargets(), "that card")
                + ". <i>(Put it onto the battlefield under your control with a +1/+1 counter. "
                + "It's a Borg artifact creature and loses all other creature types.)</i>";
    }
}
