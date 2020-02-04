

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseAbilityAttachedEffect extends ContinuousEffectImpl {

    private static final Logger logger = Logger.getLogger(LoseAbilityAttachedEffect.class);

    protected Ability ability;
    protected AttachmentType attachmentType;

    public LoseAbilityAttachedEffect(Ability ability, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        this.attachmentType = attachmentType;
        setText();
    }

    public LoseAbilityAttachedEffect(final LoseAbilityAttachedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public LoseAbilityAttachedEffect copy() {
        return new LoseAbilityAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                while (creature.getAbilities().contains(ability)) {
                    if (!creature.getAbilities().remove(ability)) {
                        // Something went wrong - ability has an other id?
                        logger.warn("ability" + ability.getRule() + "couldn't be removed.");
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature ");
        sb.append("loses ");
        sb.append(ability.getRule());
        staticText = sb.toString();
    }

}
