
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityAttachedEffect extends ContinuousEffectImpl {

    protected Ability ability;
    protected AttachmentType attachmentType;
    protected boolean independentEffect;

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType) {
        this(ability, attachmentType, Duration.WhileOnBattlefield);
    }

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType, Duration duration) {
        this(ability, attachmentType, duration, null);
    }

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType, Duration duration, String rule) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.attachmentType = attachmentType;
        switch (duration) {
            case WhileOnBattlefield:
            case WhileInGraveyard:
            case WhileOnStack:
                independentEffect = false;
                break;
            default:
                // such effects exist independent from the enchantment that created the effect
                independentEffect = true;
        }

        if (rule == null) {
            setText();
        } else {
            this.staticText = rule;
        }
        this.addDependencyType(DependencyType.AddingAbility);
    }

    public GainAbilityAttachedEffect(final GainAbilityAttachedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.attachmentType = effect.attachmentType;
        this.independentEffect = effect.independentEffect;
    }

    @Override
    public GainAbilityAttachedEffect copy() {
        return new GainAbilityAttachedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
            }
        }
        if (permanent != null) {
            permanent.addAbility(ability, source.getSourceId(), game, false);
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature ");
        if (duration == Duration.WhileOnBattlefield) {
            sb.append("has ");
        } else {
            sb.append("gains ");
        }
        sb.append(ability.getRule());
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        staticText = sb.toString();
    }

}
