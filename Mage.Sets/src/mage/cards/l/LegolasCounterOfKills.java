package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegolasCounterOfKills extends CardImpl {

    public LegolasCounterOfKills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you scry, if Legolas, Counter of Kills is tapped, you may untap it. Do this only once each turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new ScryTriggeredAbility(new UntapSourceEffect()).setDoOnlyOnceEachTurn(true), SourceTappedCondition.TAPPED,
                "Whenever you scry, if {this} is tapped, you may untap it. Do this only once each turn."
        ));

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Legolas.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private LegolasCounterOfKills(final LegolasCounterOfKills card) {
        super(card);
    }

    @Override
    public LegolasCounterOfKills copy() {
        return new LegolasCounterOfKills(this);
    }
}
