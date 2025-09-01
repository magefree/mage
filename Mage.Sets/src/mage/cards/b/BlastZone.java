package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValueEqualToCountersSourceCountPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlastZone extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "each nonland permanent with mana value equal to the number of charge counters on {this}"
    );
    static {
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.CHARGE));
    }

    public BlastZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // Blast Zone enters the battlefield with a charge counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)),
                "with a charge counter on it"
        ));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {X}{X}, {T}: Put X charge counters on Blast Zone.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), GetXValue.instance, true
        ), new ManaCostsImpl<>("{X}{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Sacrifice Blast Zone: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Blast Zone.
        ability = new SimpleActivatedAbility(new DestroyAllEffect(filter), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BlastZone(final BlastZone card) {
        super(card);
    }

    @Override
    public BlastZone copy() {
        return new BlastZone(this);
    }
}
