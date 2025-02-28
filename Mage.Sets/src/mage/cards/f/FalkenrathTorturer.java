
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class FalkenrathTorturer extends CardImpl {

    public FalkenrathTorturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice a creature: Falkenrath Torturer gains flying until end of turn.
        // If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Torturer.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificedPermanentCondition(new FilterCreaturePermanent(SubType.HUMAN, "a Human")))
                .setText("If the sacrificed creature was a Human, put a +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private FalkenrathTorturer(final FalkenrathTorturer card) {
        super(card);
    }

    @Override
    public FalkenrathTorturer copy() {
        return new FalkenrathTorturer(this);
    }
}
