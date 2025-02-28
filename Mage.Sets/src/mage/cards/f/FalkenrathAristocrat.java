package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author North
 */
public final class FalkenrathAristocrat extends CardImpl {

    public FalkenrathAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // Sacrifice a creature: Falkenrath Aristocrat is indestructible this turn.
        // If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Aristocrat.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificedPermanentCondition(new FilterCreaturePermanent(SubType.HUMAN, "a Human")))
                .setText("If the sacrificed creature was a Human, put a +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private FalkenrathAristocrat(final FalkenrathAristocrat card) {
        super(card);
    }

    @Override
    public FalkenrathAristocrat copy() {
        return new FalkenrathAristocrat(this);
    }
}