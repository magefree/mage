package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BlowflyInfestation extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public BlowflyInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever a creature dies, if it had a -1/-1 counter on it, put a -1/-1 counter on target creature.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()), false, filter
        ).setTriggerPhrase("Whenever a creature dies, if it had a -1/-1 counter on it, ");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BlowflyInfestation(final BlowflyInfestation card) {
        super(card);
    }

    @Override
    public BlowflyInfestation copy() {
        return new BlowflyInfestation(this);
    }
}
