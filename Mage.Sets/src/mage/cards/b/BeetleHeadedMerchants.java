package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeetleHeadedMerchants extends CardImpl {

    public BeetleHeadedMerchants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever this creature attacks, you may sacrifice another creature or artifact. If you do, draw a card and put a +1/+1 counter on this creature.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        ).addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"))));
    }

    private BeetleHeadedMerchants(final BeetleHeadedMerchants card) {
        super(card);
    }

    @Override
    public BeetleHeadedMerchants copy() {
        return new BeetleHeadedMerchants(this);
    }
}
