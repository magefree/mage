
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author North
 */
public class AddPoisonCounterTargetEffect extends OneShotEffect {

    protected int amount;

    public AddPoisonCounterTargetEffect(int amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public AddPoisonCounterTargetEffect(final AddPoisonCounterTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AddPoisonCounterTargetEffect copy() {
        return new AddPoisonCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.addCounters(CounterType.POISON.createInstance(amount), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " get " : " gets ") +
                CardUtil.numberToText(amount, "a") + " poison counter" + (amount > 1 ? "s" : "");
    }
}
