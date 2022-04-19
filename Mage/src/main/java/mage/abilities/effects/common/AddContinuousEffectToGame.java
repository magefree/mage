

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author noxx
 */
public class AddContinuousEffectToGame extends OneShotEffect {

    private final ContinuousEffect effect;

    public AddContinuousEffectToGame(ContinuousEffect effect) {
        super(Outcome.Benefit);
        this.effect = effect;
    }

    public AddContinuousEffectToGame(final AddContinuousEffectToGame effect) {
        super(effect);
        this.effect = effect.effect;
    }

    @Override
    public AddContinuousEffectToGame copy() {
        return new AddContinuousEffectToGame(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(effect, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return effect.getText(mode);
    }
}
