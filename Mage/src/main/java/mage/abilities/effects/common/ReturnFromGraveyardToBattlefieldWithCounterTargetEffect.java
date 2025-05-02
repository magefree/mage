package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author weirddan455
 */
public class ReturnFromGraveyardToBattlefieldWithCounterTargetEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    private final Counters counters;
    private final String counterText;

    public ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(Counter... counters) {
        this(false, counters);
    }

    public ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(boolean additional, Counter... counters) {
        super(false);
        this.counters = new Counters();
        for (Counter counter : counters) {
            this.counters.addCounter(counter);
        }
        this.counterText = makeText(additional, counters);
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

    private static String makeText(boolean additional, Counter... counters) {
        List<String> strings = new ArrayList<>();
        for (Counter counter : counters) {
            StringBuilder sb = new StringBuilder();
            if (counter.getCount() == 1) {
                if (additional) {
                    sb.append("an additional ").append(counter.getName());
                } else {
                    sb.append(CardUtil.addArticle(counter.getName()));
                }
                sb.append(" counter");
            } else {
                sb.append(CardUtil.numberToText(counter.getCount()));
                sb.append(additional ? " additional " : " ");
                sb.append(counter.getName());
                sb.append(" counters");
            }
            strings.add(sb.toString());
        }
        return " with " + CardUtil.concatWithAnd(strings);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return super.getText(mode) + counterText + (getTargetPointer().isPlural(mode.getTargets()) ? " on them" : " on it");
    }
}
