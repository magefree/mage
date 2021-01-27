package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Locale;

/**
 * @author TheElk801
 */
public class AddCounterChoiceSourceEffect extends OneShotEffect {

    private final CounterType counterType1;
    private final CounterType counterType2;

    public AddCounterChoiceSourceEffect(CounterType counterType1, CounterType counterType2) {
        super(Outcome.Benefit);
        this.counterType1 = counterType1;
        this.counterType2 = counterType2;
        staticText = "with your choice of a " + counterType1 + " counter or a " + counterType2 + " counter on it";
    }

    private AddCounterChoiceSourceEffect(final AddCounterChoiceSourceEffect effect) {
        super(effect);
        this.counterType1 = effect.counterType1;
        this.counterType2 = effect.counterType2;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Counter counter;
        if (player.chooseUse(
                Outcome.Neutral, "Choose " + counterType1 + " or " + counterType2, null,
                cap(counterType1.getName()), cap(counterType2.getName()), source, game
        )) {
            counter = counterType1.createInstance();
        } else {
            counter = counterType2.createInstance();
        }
        return permanent.addCounters(counter, source.getControllerId(), source, game);
    }

    private static final String cap(String string) {
        return string != null ? string.substring(0, 1).toUpperCase(Locale.ENGLISH) + string.substring(1) : null;
    }

    @Override
    public AddCounterChoiceSourceEffect copy() {
        return new AddCounterChoiceSourceEffect(this);
    }
}
