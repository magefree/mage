

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author markort147
 */
public class LoseAllAbilitiesAttachedEffect extends ContinuousEffectImpl {

    protected AttachmentType attachmentType;

    public LoseAllAbilitiesAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.attachmentType = attachmentType;
        setText();
    }

    protected LoseAllAbilitiesAttachedEffect(final LoseAllAbilitiesAttachedEffect effect) {
        super(effect);
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public LoseAllAbilitiesAttachedEffect copy() {
        return new LoseAllAbilitiesAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                creature.removeAllAbilities(source.getSourceId(), game);
            }
        }
        return true;
    }

    private void setText() {
        staticText = attachmentType.verb() + " creature loses all abilities";
    }

}
