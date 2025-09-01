package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValueEqualToCountersSourceCountPredicate;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class PowderKeg extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(
            "each artifact and creature with mana value equal to the number of fuse counters on {this}"
    );
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.FUSE));
    }

    public PowderKeg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, you may put a fuse counter on Powder Keg.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.FUSE.createInstance(), true), true));

        // {T}, Sacrifice Powder Keg: Destroy each artifact and creature with converted mana cost equal to the number of fuse counters on Powder Keg.
        Ability ability = new SimpleActivatedAbility(new DestroyAllEffect(filter), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PowderKeg(final PowderKeg card) {
        super(card);
    }

    @Override
    public PowderKeg copy() {
        return new PowderKeg(this);
    }
}
