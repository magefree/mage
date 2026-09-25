package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author muz
 */
public final class GuidingHydra extends CardImpl {

    public GuidingHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{W}");

        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(0);

        // This creature enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // At the beginning of combat on your turn, you may remove a +1/+1 counter from this creature. If you do, put a +1/+1 counter on each other creature you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoIfCostPaid(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE),
            new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
        )));
    }

    private GuidingHydra(final GuidingHydra card) {
        super(card);
    }

    @Override
    public GuidingHydra copy() {
        return new GuidingHydra(this);
    }
}
