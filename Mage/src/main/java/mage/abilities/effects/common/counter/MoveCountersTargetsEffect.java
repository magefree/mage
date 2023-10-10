
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Styxo
 */
public class MoveCountersTargetsEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;

    public MoveCountersTargetsEffect(CounterType counterType, int amount) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        this.amount = amount;

    }

    protected MoveCountersTargetsEffect(final MoveCountersTargetsEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public MoveCountersTargetsEffect copy() {
        return new MoveCountersTargetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent removeTargetCreature = game.getPermanent(targetPointer.getTargets(game, source).get(0));
        Permanent addTargetCreature = game.getPermanent(targetPointer.getTargets(game, source).get(1));
        if (removeTargetCreature != null && addTargetCreature != null && removeTargetCreature.getCounters(game).getCount(counterType) >= amount) {
            removeTargetCreature.removeCounters(counterType.createInstance(amount), source, game);
            addTargetCreature.addCounters(counterType.createInstance(amount), source.getControllerId(), source, game);
            if (!game.isSimulation()) {
                game.informPlayers("Moved " + amount + ' ' + counterType.getName() + " counter" + (amount > 1 ? "s" : "") + " from " + removeTargetCreature.getLogName() + " to " + addTargetCreature.getLogName());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder("move ");
        if (amount > 1) {
            sb.append(amount);
        } else {
            sb.append('a');
        }
        sb.append(' ');
        sb.append(counterType.getName());
        sb.append(" counter");
        if (amount > 1) {
            sb.append("s ");
        } else {
            sb.append(' ');
        }
        sb.append("from one target creature to another target creature");

        return sb.toString();
    }
}
