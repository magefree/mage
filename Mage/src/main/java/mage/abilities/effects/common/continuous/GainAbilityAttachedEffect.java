package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.targetpointer.SourceAttachedTargetPointer;

import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityAttachedEffect extends GainAbilityTargetEffect {
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
        super(ability, duration, rule);
        String name;
        if (attachmentType == null) {
            name = "";
        } else {
            name = attachmentType.verb().toLowerCase() + " " + targetObjectName;
        }
        this.setTargetPointer(new SourceAttachedTargetPointer(name));
        this.targetObjectName = targetObjectName;
    }

    protected GainAbilityAttachedEffect(final GainAbilityAttachedEffect effect) {
        super(effect);
        this.doesntRemoveItself = effect.doesntRemoveItself;
    }

    @Override
    public GainAbilityAttachedEffect copy() {
        return new GainAbilityAttachedEffect(this);
    }


    public GainAbilityAttachedEffect setDoesntRemoveItself(boolean doesntRemoveItself) {
        this.doesntRemoveItself = doesntRemoveItself;
        return this;
    }

    @Override
    protected List<Ability> getAbilitiesToGrant(Game game, Ability source) {
        if (!doesntRemoveItself) {
            return super.getAbilitiesToGrant(game, source);
        }
        List<Ability> granted = copyOfGrantedAbilities();
        granted.stream()
                .filter(ProtectionAbility.class::isInstance)
                .forEach(ability -> ((ProtectionAbility) ability).setAuraIdNotToBeRemoved(source.getSourceId()));
        return granted;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null) {
            return staticText;
        }
        return super.getText(mode) + (doesntRemoveItself ? ". This effect doesn't remove {this}." : "");
    }
}
