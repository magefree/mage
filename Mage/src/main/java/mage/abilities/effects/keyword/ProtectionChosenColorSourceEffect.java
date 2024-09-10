
package mage.abilities.effects.keyword;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class ProtectionChosenColorSourceEffect extends ContinuousEffectImpl {

    protected ObjectColor chosenColor;
    protected ProtectionAbility protectionAbility;

    public ProtectionChosenColorSourceEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has protection from the chosen color";
    }

    protected ProtectionChosenColorSourceEffect(final ProtectionChosenColorSourceEffect effect) {
        super(effect);
        if (effect.chosenColor != null) {
            this.chosenColor = effect.chosenColor.copy();
        }
        if (effect.protectionAbility != null) {
            this.protectionAbility = effect.protectionAbility.copy();
        }
    }

    @Override
    public ProtectionChosenColorSourceEffect copy() {
        return new ProtectionChosenColorSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (color != null && (protectionAbility == null || !color.equals(chosenColor))) {
                chosenColor = color;
                FilterObject protectionFilter = new FilterObject(chosenColor.getDescription());
                protectionFilter.add(new ColorPredicate(chosenColor));
                protectionAbility = new ProtectionAbility(protectionFilter);
            }
            if (protectionAbility != null) {
                permanent.addAbility(protectionAbility, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }
}
