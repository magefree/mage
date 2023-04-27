package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrillworksMole extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("commander creature you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public DrillworksMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.MOLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, {T}: Put a +1/+1 counter on Drillworks Mole and a +1/+1 counter on up to one target commander creature you control.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("and a +1/+1 counter on up to one target commander creature you control"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DrillworksMole(final DrillworksMole card) {
        super(card);
    }

    @Override
    public DrillworksMole copy() {
        return new DrillworksMole(this);
    }
}
