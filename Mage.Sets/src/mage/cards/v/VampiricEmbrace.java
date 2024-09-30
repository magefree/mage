package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedAndDiedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class VampiricEmbrace extends CardImpl {

    public VampiricEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has flying.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever a creature dealt damage by enchanted creature this turn dies, put a +1/+1 counter on that creature.
        this.addAbility(new DealtDamageAttachedAndDiedTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT, AttachmentType.AURA));
    }

    private VampiricEmbrace(final VampiricEmbrace card) {
        super(card);
    }

    @Override
    public VampiricEmbrace copy() {
        return new VampiricEmbrace(this);
    }
}
