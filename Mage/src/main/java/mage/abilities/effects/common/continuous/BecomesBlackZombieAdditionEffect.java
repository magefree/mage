
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author JRHerlehy Created on 4/8/17.
 */
public class BecomesBlackZombieAdditionEffect extends ContinuousEffectImpl {

    private boolean giveBlackColor = true;

    public BecomesBlackZombieAdditionEffect() {
        super(Duration.Custom, Outcome.Neutral);
        this.giveBlackColor = true;
        updateText();
    }

    public BecomesBlackZombieAdditionEffect(boolean giveBlackColor) {
        this();
        this.giveBlackColor = giveBlackColor;
        updateText();
    }


    public BecomesBlackZombieAdditionEffect(final BecomesBlackZombieAdditionEffect effect) {
        super(effect);
        this.giveBlackColor = effect.giveBlackColor;
        updateText();
    }

    private void updateText() {
        if (this.giveBlackColor) {
            this.staticText = "That creature is a black Zombie in addition to its other colors and types";
        } else {
            this.staticText = "That creature is a Zombie in addition to its other types";
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    creature.addSubType(game, SubType.ZOMBIE);
                    break;
                case ColorChangingEffects_5:
                    if (this.giveBlackColor) {
                        creature.getColor(game).setBlack(true);
                    }
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesBlackZombieAdditionEffect copy() {
        return new BecomesBlackZombieAdditionEffect(this);
    }
}
