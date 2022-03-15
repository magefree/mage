package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.*;
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
    protected String targetObjectName;
    protected boolean doesntRemoveItself = false;

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType) {
        this(ability, attachmentType, Duration.WhileOnBattlefield);
    }

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType, Duration duration) {
        this(ability, attachmentType, duration, null);
    }

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType, Duration duration, String rule) {
        this(ability, attachmentType, duration, rule, "creature");
    }

    public GainAbilityAttachedEffect(Ability ability, AttachmentType attachmentType, Duration duration, String rule, String targetObjectName) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.targetObjectName = targetObjectName;
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

        if (rule != null) {
            this.staticText = rule;
        }

        this.generateGainAbilityDependencies(ability, null);
    }

    public GainAbilityAttachedEffect(final GainAbilityAttachedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.attachmentType = effect.attachmentType;
        this.independentEffect = effect.independentEffect;
        this.targetObjectName = effect.targetObjectName;
        this.doesntRemoveItself = effect.doesntRemoveItself;
    }

    @Override
    public GainAbilityAttachedEffect copy() {
        return new GainAbilityAttachedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent;
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
            } else {
                permanent = null;
            }
        }
        if (permanent != null) {
            if (doesntRemoveItself && ability instanceof ProtectionAbility) {
                ((ProtectionAbility) ability).setAuraIdNotToBeRemoved(source.getSourceId());
            }
            permanent.addAbility(ability, source.getSourceId(), game);
            afterGain(game, source, permanent, ability);
        }
        return true;
    }

    /**
     * Calls after ability gain. Override it to apply additional data (example: transfer ability's settings from original to destination source)
     *
     * @param game
     * @param source
     * @param permanent
     * @param addedAbility
     */
    public void afterGain(Game game, Ability source, Permanent permanent, Ability addedAbility) {
        //
    }

    public GainAbilityAttachedEffect setDoesntRemoveItself(boolean doesntRemoveItself) {
        this.doesntRemoveItself = doesntRemoveItself;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb().toLowerCase());
        sb.append(" " + targetObjectName + " ");
        if (duration == Duration.WhileOnBattlefield) {
            sb.append("has ");
        } else {
            sb.append("gains ");
        }
        boolean quotes = (ability instanceof SimpleActivatedAbility) || (ability instanceof TriggeredAbility);
        if (quotes) {
            sb.append('"');
        }
        sb.append(ability.getRule("this " + targetObjectName));
        if (quotes) {
            sb.append('"');
        }
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration);
        }
        if (doesntRemoveItself) {
            sb.append(" This effect doesn't remove {this}.");
        }
        return sb.toString();
    }
}
