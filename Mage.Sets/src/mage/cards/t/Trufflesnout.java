package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Trufflesnout extends CardImpl {

    public Trufflesnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Trufflesnout enters the battlefield, choose one —
        // • Put a +1/+1 counter on Trufflesnout.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );

        // • You gain 4 life.
        ability.addMode(new Mode(new GainLifeEffect(4)));
        this.addAbility(ability);
    }

    private Trufflesnout(final Trufflesnout card) {
        super(card);
    }

    @Override
    public Trufflesnout copy() {
        return new Trufflesnout(this);
    }
}
