
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
public class ProtectionChosenColorAttachedEffect extends ContinuousEffectImpl {

    protected ObjectColor chosenColor;
    protected ProtectionAbility protectionAbility;
    protected final boolean notRemoveItself;
    protected boolean notRemoveControlled;

    public ProtectionChosenColorAttachedEffect(boolean notRemoveItself) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.notRemoveItself = notRemoveItself;
        staticText = "enchanted creature has protection from the chosen color" + (notRemoveItself ? ". This effect doesn't remove {this}" : "");
    }

    public ProtectionChosenColorAttachedEffect(final ProtectionChosenColorAttachedEffect effect) {
        super(effect);
        if (effect.chosenColor != null) {
            this.chosenColor = effect.chosenColor.copy();
        }
        if (effect.protectionAbility != null) {
            this.protectionAbility = effect.protectionAbility.copy();
        }
        this.notRemoveItself = effect.notRemoveItself;
        this.notRemoveControlled = effect.notRemoveControlled;
    }

    @Override
    public ProtectionChosenColorAttachedEffect copy() {
        return new ProtectionChosenColorAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachement = game.getPermanent(source.getSourceId());
        if (attachement != null && attachement.getAttachedTo() != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(attachement.getId() + "_color");
            if (color != null && (protectionAbility == null || !color.equals(chosenColor))) {
                chosenColor = color;
                FilterObject protectionFilter = new FilterObject(chosenColor.getDescription());
                protectionFilter.add(new ColorPredicate(chosenColor));
                protectionAbility = new ProtectionAbility(protectionFilter);
                if (notRemoveItself) {
                    protectionAbility.setAuraIdNotToBeRemoved(source.getSourceId());
                }
                if (notRemoveControlled) {
                    protectionAbility.setDoesntRemoveControlled(true);
                    protectionAbility.setRemoveEquipment(false);
                    protectionAbility.setRemovesAuras(false);
                }
            }
            if (protectionAbility != null) {
                Permanent attachedTo = game.getPermanent(attachement.getAttachedTo());
                if (attachedTo != null) {
                    attachedTo.addAbility(protectionAbility, source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }

    public ProtectionChosenColorAttachedEffect setNotRemoveControlled(boolean notRemoveControlled) {
        this.notRemoveControlled = notRemoveControlled;
        return this;
    }
}
