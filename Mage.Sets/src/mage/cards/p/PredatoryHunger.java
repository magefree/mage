
package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class PredatoryHunger extends CardImpl {

    public PredatoryHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever an opponent casts a creature spell, put a +1/+1 counter on enchanted creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new AddCountersAttachedEffect(
                        CounterType.P1P1.createInstance(),
                        "enchanted creature"
                ), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));
    }

    private PredatoryHunger(final PredatoryHunger card) {
        super(card);
    }

    @Override
    public PredatoryHunger copy() {
        return new PredatoryHunger(this);
    }
}
