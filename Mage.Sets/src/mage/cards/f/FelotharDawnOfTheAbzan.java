package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FelotharDawnOfTheAbzan extends CardImpl {

    public FelotharDawnOfTheAbzan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Felothar enters or attacks, you may sacrifice a nonland permanent. When you do, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoWhenCostPaid(
                new ReflexiveTriggeredAbility(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), false),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
                "Sacrifice a nonland permanent?"
        )));
    }

    private FelotharDawnOfTheAbzan(final FelotharDawnOfTheAbzan card) {
        super(card);
    }

    @Override
    public FelotharDawnOfTheAbzan copy() {
        return new FelotharDawnOfTheAbzan(this);
    }
}
