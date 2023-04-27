package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
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
 * @author TheElk801
 */
public final class DuelingCoach extends CardImpl {

    public DuelingCoach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Dueling Coach enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {4}{W}, {T}: Put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1),
                new ManaCostsImpl<>("{4}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DuelingCoach(final DuelingCoach card) {
        super(card);
    }

    @Override
    public DuelingCoach copy() {
        return new DuelingCoach(this);
    }
}
