package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Voidpouncer extends CardImpl {

    public Voidpouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Kicker {2}{C}
        this.addAbility(new KickerAbility("{2}{C}"));

        // If Voidpouncer was kicked, it enters the battlefield with two +1/+1 counters and a trample counter on it and with haste.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counter and a trample counter on it and with haste.", ""
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.TRAMPLE.createInstance()));
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom));
        this.addAbility(ability);
    }

    private Voidpouncer(final Voidpouncer card) {
        super(card);
    }

    @Override
    public Voidpouncer copy() {
        return new Voidpouncer(this);
    }
}