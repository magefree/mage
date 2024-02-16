package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author weirddan455
 */
public class ReturnFromGraveyardToBattlefieldWithCounterTargetEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    private final Counters counters;
    private final String counterText;

    public ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(Counter counter) {
        this(counter, false);
    }

    public ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(Counter counter, boolean additional) {
        super(false);
        this.counters = new Counters();
        this.counters.addCounter(counter);
        this.counterText = makeText(counter, additional);
    }

    protected ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(final ReturnFromGraveyardToBattlefieldWithCounterTargetEffect effect) {
        super(effect);
        this.counters = effect.counters.copy();
        this.counterText = effect.counterText;
    }

    @Override
    public ReturnFromGraveyardToBattlefieldWithCounterTargetEffect copy() {
        return new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            game.setEnterWithCounters(targetId, counters.copy());
        }
        return super.apply(game, source);
    }

    private String makeText(Counter counter, boolean additional) {
        StringBuilder sb = new StringBuilder(" with ");
        if (additional) {
            if (counter.getCount() == 1) {
                sb.append("an");
            } else {
                sb.append(counter.getCount());
            }
            sb.append(" additional");
        } else if (counter.getCount() == 1) {
            sb.append("a");
        } else {
            sb.append(counter.getCount());
        }
        sb.append(' ');
        sb.append(counter.getName());
        sb.append(" counter");
        if (counter.getCount() != 1) {
            sb.append('s');
        }
        return sb.toString();
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return super.getText(mode) + counterText + (getTargetPointer().isPlural(mode.getTargets()) ? " on them" : " on it");
    }
}
