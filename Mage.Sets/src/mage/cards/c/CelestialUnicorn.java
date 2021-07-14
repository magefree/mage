package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
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
public final class CelestialUnicorn extends CardImpl {

    public CelestialUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put a +1/+1 counter on Celestial Unicorn.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private CelestialUnicorn(final CelestialUnicorn card) {
        super(card);
    }

    @Override
    public CelestialUnicorn copy() {
        return new CelestialUnicorn(this);
    }
}
