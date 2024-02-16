
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class BlindWorship extends CardImpl {

    public BlindWorship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature attacks, each other creature you control gets +X/+X until end of turn, where X is this creature's power."
        DynamicValue xValue = new SourcePermanentPowerCount();
        Ability attachedAbility = new AttacksTriggeredAbility(new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true), false);
        Effect gainAbilityEffect = new GainAbilityAttachedEffect(attachedAbility, AttachmentType.AURA);
        gainAbilityEffect.setText("Enchanted creature has \"Whenever this creature attacks, each other creature you control gets +X/+X until end of turn, where X is this creature's power\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainAbilityEffect));

    }

    private BlindWorship(final BlindWorship card) {
        super(card);
    }

    @Override
    public BlindWorship copy() {
        return new BlindWorship(this);
    }
}
