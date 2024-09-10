package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FirstTargetPointer;

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

    protected AddContinuousEffectToGame(final AddContinuousEffectToGame effect) {
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
            // runtime check
            if (!effect.getTargetPointer().getClass().equals(FirstTargetPointer.class)
                    && !effect.getTargetPointer().getClass().equals(this.getTargetPointer().getClass())) {
                throw new IllegalArgumentException("Wrong code usage: found diff target pointers, must set target pointers to AddContinuousEffectToGame, not to inner effects"
                        + " - " + source.getClass().getSimpleName()
                        + " - " + source
                );
            }
            effect.setTargetPointer(this.getTargetPointer().copy());
            game.addEffect((ContinuousEffect) effect, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return effects.getText(mode);
    }
}
