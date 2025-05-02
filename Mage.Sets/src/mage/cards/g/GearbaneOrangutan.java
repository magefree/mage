package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GearbaneOrangutan extends CardImpl {

    public GearbaneOrangutan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.APE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Gearbane Orangutan enters the battlefield, choose one --
        // * Destroy up to one target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetArtifactPermanent(0, 1));

        // * Sacrifice an artifact. If you do, put two +1/+1 counters on Gearbane Orangutan.
        ability.addMode(new Mode(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN),
                null, false
        )));
        this.addAbility(ability);
    }

    private GearbaneOrangutan(final GearbaneOrangutan card) {
        super(card);
    }

    @Override
    public GearbaneOrangutan copy() {
        return new GearbaneOrangutan(this);
    }
}
