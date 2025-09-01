package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HogMonkey extends CardImpl {

    public HogMonkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, target creature you control with a +1/+1 counter on it gains menace until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(new MenaceAbility(false)));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1));
        this.addAbility(ability);

        // Exhaust -- {5}: Put two +1/+1 counters on this creature.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new GenericManaCost(5)
        ));
    }

    private HogMonkey(final HogMonkey card) {
        super(card);
    }

    @Override
    public HogMonkey copy() {
        return new HogMonkey(this);
    }
}
