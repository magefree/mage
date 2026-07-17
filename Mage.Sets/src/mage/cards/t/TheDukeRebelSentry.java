package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDukeRebelSentry extends CardImpl {

    public TheDukeRebelSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // The Duke enters with a +1/+1 counter on him.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on him"
        ));

        // {T}, Remove a counter from The Duke: Put a +1/+1 counter on another target creature you control. It gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TapSourceCost()
        );
        ability.addCost(new RemoveCountersSourceCost(1));
        ability.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("It gains hexproof until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private TheDukeRebelSentry(final TheDukeRebelSentry card) {
        super(card);
    }

    @Override
    public TheDukeRebelSentry copy() {
        return new TheDukeRebelSentry(this);
    }
}
