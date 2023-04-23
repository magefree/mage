package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author noxx
 */
public class AddContinuousEffectToGame extends OneShotEffect {

    private final Effects effects = new Effects();

    public AddContinuousEffectToGame(ContinuousEffect... effects) {
        super(Outcome.Benefit);
        for (ContinuousEffect effect : effects) {
            this.effects.add(effect);
        }
    }

    public AddContinuousEffectToGame(final AddContinuousEffectToGame effect) {
        super(effect);
        this.effects.addAll(effect.effects);
    }

    @Override
    public AddContinuousEffectToGame copy() {
        return new AddContinuousEffectToGame(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Effect effect : this.effects) {
            game.addEffect((ContinuousEffect) effect, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return effects.getText(mode);
    }
}
