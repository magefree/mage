package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
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
    private final Effect effect;

    public IfAbilityHasResolvedXTimesEffect(Outcome outcome, int resolutionNumber, Effect effect) {
        super(outcome);
        this.resolutionNumber = resolutionNumber;
        this.effect = effect;
        this.staticText = "If this is the " + CardUtil.numberToOrdinalText(resolutionNumber) + " time this ability has resolved this turn, " +
                effect.getText(null);
    }

    private IfAbilityHasResolvedXTimesEffect(final IfAbilityHasResolvedXTimesEffect effect) {
        super(effect);
        this.resolutionNumber = effect.resolutionNumber;
        this.effect = effect.effect;
    }

    @Override
    public IfAbilityHasResolvedXTimesEffect copy() {
        return new IfAbilityHasResolvedXTimesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityResolvedWatcher.getResolutionCount(game, source) != resolutionNumber) {
            return true;
        }
        if (effect instanceof OneShotEffect) {
            return effect.apply(game, source);
        }
        game.addEffect((ContinuousEffect) effect, source);
        return true;
    }
}
