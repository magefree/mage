package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrSuspendedCard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public class TimeTravelEffect extends OneShotEffect {

    public TimeTravelEffect() {
        this("", true);
    }

    public TimeTravelEffect(boolean showAbilityHint) {
        this("", showAbilityHint);
    }

    public TimeTravelEffect(String afterText, boolean showAbilityHint) {
        super(Outcome.Benefit);
        staticText = "time travel" + afterText;
        if (showAbilityHint) {
            staticText += ". <i>(For each suspended card you own and each permanent you control with a time counter on it, you may add or remove a time counter.)</i>";
        }
    }

    public TimeTravelEffect(TimeTravelEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent you control with a time counter or suspended card you own to ADD a time counter to");
        filter.getPermanentFilter().add(TargetController.YOU.getControllerPredicate());
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
        filter.getCardFilter().add(TargetController.YOU.getOwnerPredicate());

        Target target = new TargetPermanentOrSuspendedCard(filter, true, 0, Integer.MAX_VALUE);
        Map<String, Serializable> options = new HashMap<>();
        options.put("UI.right.btn.text", "Done");
        controller.choose(Outcome.Benefit, target, source, game, options);

        // Adding time counters
        for (UUID chosen : target.getTargets()) {
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                permanent.addCounters(CounterType.TIME.createInstance(), source.getControllerId(), source, game);
                game.informPlayers(permanent.getName() + " had a time counter added to it.");
                filter.getPermanentFilter().add(Predicates.not(new CardIdPredicate(chosen)));
                continue;
            }
            Card card = game.getCard(chosen);
            if (card != null) {
                card.addCounters(CounterType.TIME.createInstance(), source.getControllerId(), source, game);
                game.informPlayers(card.getName() + " had a time counter added to it.");
                filter.getCardFilter().add(Predicates.not(new CardIdPredicate(chosen)));
            }
        }

        // Removing time counters
        filter.setMessage("permanent you control with a time counter or suspended card you own to REMOVE a time counter from");
        target = new TargetPermanentOrSuspendedCard(filter, true, 0, Integer.MAX_VALUE);
        controller.choose(Outcome.Benefit, target, source, game, options);
        for (UUID chosen : target.getTargets()) {
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                permanent.removeCounters(CounterType.TIME.createInstance(), source, game);
                game.informPlayers(permanent.getName() + " had a time counter removed from it.");
                continue;
            }
            Card card = game.getCard(chosen);
            if (card != null) {
                card.removeCounters(CounterType.TIME.createInstance(), source, game);
                game.informPlayers(card.getName() + " had a time counter removed from it.");
            }
        }

        return true;
    }

    @Override
    public TimeTravelEffect copy() {
        return new TimeTravelEffect(this);
    }
}
