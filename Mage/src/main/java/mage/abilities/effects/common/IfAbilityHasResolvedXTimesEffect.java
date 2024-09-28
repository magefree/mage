package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.AbilityResolvedWatcher;

/**
 * @author emerald000
 */
public class IfAbilityHasResolvedXTimesEffect extends OneShotEffect {

    private final int resolutionNumber;
    private final Effects effects;
    private final boolean orMore;

    public IfAbilityHasResolvedXTimesEffect(int resolutionNumber, Effect effect) {
        this(effect.getOutcome(), resolutionNumber, effect);
    }

    public IfAbilityHasResolvedXTimesEffect(Outcome outcome, int resolutionNumber, Effect... effects) {
        this(outcome, resolutionNumber, false, effects);
    }

    public IfAbilityHasResolvedXTimesEffect(Outcome outcome, int resolutionNumber, boolean orMore, Effect... effects) {
        super(outcome);
        this.resolutionNumber = resolutionNumber;
        this.effects = new Effects(effects);
        this.orMore = orMore;
    }

    private IfAbilityHasResolvedXTimesEffect(final IfAbilityHasResolvedXTimesEffect effect) {
        super(effect);
        this.resolutionNumber = effect.resolutionNumber;
        this.effects = effect.effects.copy();
        this.orMore = effect.orMore;
    }

    @Override
    public IfAbilityHasResolvedXTimesEffect copy() {
        return new IfAbilityHasResolvedXTimesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int resolutionCount = AbilityResolvedWatcher.getResolutionCount(game, source);
        if (resolutionCount < resolutionNumber || (!orMore && resolutionCount > resolutionNumber)) {
            return false;
        }
        boolean result = false;
        for (Effect effect : effects) {
            if (effect instanceof OneShotEffect) {
                result |= effect.apply(game, source);
                continue;
            }
            game.addEffect((ContinuousEffect) effect, source);
            result = true;
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (orMore) {
            return "otherwise, " + effects.getText(mode);
        }
        return "if this is the " + CardUtil.numberToOrdinalText(resolutionNumber) +
                " time this ability has resolved this turn, " + effects.getText(mode);
    }
}
