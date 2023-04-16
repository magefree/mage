package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThopterMechanic extends CardImpl {

    public ThopterMechanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you draw your second card each turn, put a +1/+1 counter on Thopter Mechanic.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2
        ));

        // When Thopter Mechanic dies, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));
    }

    private ThopterMechanic(final ThopterMechanic card) {
        super(card);
    }

    @Override
    public ThopterMechanic copy() {
        return new ThopterMechanic(this);
    }
}
