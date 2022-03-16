
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class EternalThirst extends CardImpl {

    public EternalThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has lifelink
        Effect effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA);
        effect.setText("Enchanted creature has lifelink");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        // and "Whenever a creature an opponent controls dies, put a +1/+1 counter on this creature."
        effect = new GainAbilityAttachedEffect(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE), AttachmentType.AURA);
        ability.addEffect(effect);
        effect.setText("and \"Whenever a creature an opponent controls dies, put a +1/+1 counter on this creature.\"");
        this.addAbility(ability);
    }

    private EternalThirst(final EternalThirst card) {
        super(card);
    }

    @Override
    public EternalThirst copy() {
        return new EternalThirst(this);
    }
}
