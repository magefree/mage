package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AgentPhilCoulson extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HERO, "other Hero you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AgentPhilCoulson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Put a +1/+1 counter on each other Hero you control.
        this.addAbility(new SimpleActivatedAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
            new TapSourceCost()
        ));
    }

    private AgentPhilCoulson(final AgentPhilCoulson card) {
        super(card);
    }

    @Override
    public AgentPhilCoulson copy() {
        return new AgentPhilCoulson(this);
    }
}
