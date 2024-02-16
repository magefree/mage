
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class FungalBloom extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Fungus");

    static {
        filter.add(SubType.FUNGUS.getPredicate());
    }

    public FungalBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{G}");

        // {G}{G}: Put a spore counter on target Fungus.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.SPORE.createInstance()),
                new ManaCostsImpl<>("{G}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FungalBloom(final FungalBloom card) {
        super(card);
    }

    @Override
    public FungalBloom copy() {
        return new FungalBloom(this);
    }
}
