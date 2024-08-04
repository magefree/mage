package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.IsDealtDamageAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MortalWound extends CardImpl {

    public MortalWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // When enchanted creature is dealt damage, destroy it.
        this.addAbility(new IsDealtDamageAttachedTriggeredAbility(
                new DestroyAttachedToEffect("it"), false, "enchanted"
        ).setTriggerPhrase("When enchanted creature is dealt damage, "));
    }

    private MortalWound(final MortalWound card) {
        super(card);
    }

    @Override
    public MortalWound copy() {
        return new MortalWound(this);
    }
}
