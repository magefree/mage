package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AerialDoombot extends CardImpl {

    public AerialDoombot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Power-up -- {5}{U}: Put three +1/+1 counters on this creature.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), new ManaCostsImpl<>("{5}{U}")
        ));
    }

    private AerialDoombot(final AerialDoombot card) {
        super(card);
    }

    @Override
    public AerialDoombot copy() {
        return new AerialDoombot(this);
    }
}
