

package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

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

    protected LoseAbilityAttachedEffect(final LoseAbilityAttachedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            if (getAffectedObjectsSet()) {
                this.discard();
            }
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.removeAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent;
        if (getAffectedObjectsSet()) {
            permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment == null || equipment.getAttachedTo() == null) {
                return Collections.emptyList();
            }
            permanent = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
        }
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }

    @Override
    public LoseAbilityAttachedEffect copy() {
        return new LoseAbilityAttachedEffect(this);
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
