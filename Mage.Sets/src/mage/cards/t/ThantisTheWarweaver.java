package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class ThantisTheWarweaver extends CardImpl {

    public ThantisTheWarweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // All creatures attack each combat if able.
        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_ALL_CREATURES)));

        // Whenever a creature attacks you or a planeswalker you control, put a +1/+1 counter on Thantis the Warweaver.
        this.addAbility(new AttacksAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, true
        ));
    }

    private ThantisTheWarweaver(final ThantisTheWarweaver card) {
        super(card);
    }

    @Override
    public ThantisTheWarweaver copy() {
        return new ThantisTheWarweaver(this);
    }
}
