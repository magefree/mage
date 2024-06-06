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

    public IfAbilityHasResolvedXTimesEffect(int resolutionNumber, Effect effect) {
        this(effect.getOutcome(), resolutionNumber, effect);
    }

    public IfAbilityHasResolvedXTimesEffect(Outcome outcome, int resolutionNumber, Effect... effect) {
        super(outcome);
        this.resolutionNumber = resolutionNumber;
        this.effects = new Effects(effect);
    }

    private IfAbilityHasResolvedXTimesEffect(final IfAbilityHasResolvedXTimesEffect effect) {
        super(effect);
        this.resolutionNumber = effect.resolutionNumber;
        this.effects = effect.effects.copy();
    }

    @Override
    public IfAbilityHasResolvedXTimesEffect copy() {
        return new IfAbilityHasResolvedXTimesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityResolvedWatcher.getResolutionCount(game, source) != resolutionNumber) {
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
        return "if this is the " + CardUtil.numberToOrdinalText(resolutionNumber) +
                " time this ability has resolved this turn, " + effects.getText(mode);
    }
}
