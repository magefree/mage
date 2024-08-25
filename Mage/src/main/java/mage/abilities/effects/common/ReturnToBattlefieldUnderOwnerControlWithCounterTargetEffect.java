package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public class ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect extends ReturnToBattlefieldUnderOwnerControlTargetEffect {

    private final Counters counters;
    private final String counterText;

    public ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(Counter counter, boolean returnFromExileZoneOnly) {
        this(counter, false, returnFromExileZoneOnly);
    }

    public ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(Counter counter, boolean additional, boolean returnFromExileZoneOnly) {
        super(false, returnFromExileZoneOnly);
        this.counters = new Counters();
        this.counters.addCounter(counter);
        this.counterText = makeText(counter, additional);
    }

    protected ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(final ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect effect) {
        super(effect);
        this.counters = effect.counters.copy();
        this.counterText = effect.counterText;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID cardId : getCardsToReturn(game, source)) {
            game.setEnterWithCounters(cardId, counters.copy());
        }
        return super.apply(game, source);
    }

    private static String makeText(Counter counter, boolean additional) {
        StringBuilder sb = new StringBuilder(" with ");
        if (additional) {
            sb.append(CardUtil.numberToText(counter.getCount(), "an"));
            sb.append(" additional");
        } else {
            sb.append(CardUtil.numberToText(counter.getCount(), "a"));
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