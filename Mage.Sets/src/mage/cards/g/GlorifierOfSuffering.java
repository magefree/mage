package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class GlorifierOfSuffering extends CardImpl {

    public GlorifierOfSuffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Glorifier of Suffering enters the battlefield, you may sacrifice another creature or artifact. When you do, put a +1/+1 counter on each of up to two target creatures.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of up to two target creatures"),
                false);
        reflexive.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                reflexive, new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT_SHORT_TEXT),
                "Sacrifice another creature or artifact?"
        )));

    }

    private GlorifierOfSuffering(final GlorifierOfSuffering card) {
        super(card);
    }

    @Override
    public GlorifierOfSuffering copy() {
        return new GlorifierOfSuffering(this);
    }
}
