package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcadeCabinet extends CardImpl {

    public ArcadeCabinet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When this artifact enters, put a +1/+1 counter on each of up to four target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 4));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice a token: Double the number of counters on target creature.
        Ability ability1 = new SimpleActivatedAbility(new DoubleCountersTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_TOKEN));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArcadeCabinet(final ArcadeCabinet card) {
        super(card);
    }

    @Override
    public ArcadeCabinet copy() {
        return new ArcadeCabinet(this);
    }
}
