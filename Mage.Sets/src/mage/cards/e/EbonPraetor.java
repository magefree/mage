
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EbonPraetor extends CardImpl {

    public EbonPraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, put a -2/-2 counter on Ebon Praetor.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M2M2.createInstance())));

        // Sacrifice a creature: Remove a -2/-2 counter from Ebon Praetor. If the sacrificed creature was a Thrull, put a +1/+0 counter on Ebon Praetor. Activate this ability only during your upkeep and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.M2M2.createInstance()),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE), 1, new IsStepCondition(PhaseStep.UPKEEP));
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P0.createInstance()),
                new SacrificedPermanentCondition(new FilterCreaturePermanent(SubType.THRULL, "a Thrull"), "If the sacrificed creature was a Thrull")
        ));
        this.addAbility(ability);
    }

    private EbonPraetor(final EbonPraetor card) {
        super(card);
    }

    @Override
    public EbonPraetor copy() {
        return new EbonPraetor(this);
    }
}