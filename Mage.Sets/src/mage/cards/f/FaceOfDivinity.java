package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class FaceOfDivinity extends CardImpl {

    public FaceOfDivinity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));

        // As long as another Aura is attached to enchanted creature, it has first strike and lifelink.
        Effect effect1 = new ConditionalContinuousEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA),
                FaceOfDivinityCondition.instance, "As long as another Aura is attached to enchanted creature, it has first strike");
        Effect effect2 = new ConditionalContinuousEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA),
                FaceOfDivinityCondition.instance, "and lifelink");
        Ability abilityBoost = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        abilityBoost.addEffect(effect2);
        this.addAbility(abilityBoost);
    }

    public FaceOfDivinity(final FaceOfDivinity card) {
        super(card);
    }

    @Override
    public FaceOfDivinity copy() {
        return new FaceOfDivinity(this);
    }

}

enum FaceOfDivinityCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent currentAura = game.getPermanent(source.getSourceId());
        if (currentAura != null && currentAura.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(currentAura.getAttachedTo());
            if (permanent != null && !permanent.getAttachments().isEmpty()) {
                for (UUID id : permanent.getAttachments()) {
                    Permanent otherAura = game.getPermanent(id);
                    if (otherAura != null && !otherAura.getId().equals(currentAura.getId())
                            && otherAura.getSubtype(game).contains(SubType.AURA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "another Aura is attached to enchanted creature";
    }
}


