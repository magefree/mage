package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DapperShieldmate extends CardImpl {

    public DapperShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dapper Shieldmate enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // As long as it's your turn, Dapper Shieldmate gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "as long as it's your turn, {this} gets +2/+0"
        )));
    }

    private DapperShieldmate(final DapperShieldmate card) {
        super(card);
    }

    @Override
    public DapperShieldmate copy() {
        return new DapperShieldmate(this);
    }
}
