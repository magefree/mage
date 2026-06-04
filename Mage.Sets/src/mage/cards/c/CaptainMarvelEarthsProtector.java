package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainMarvelEarthsProtector extends CardImpl {

    public CaptainMarvelEarthsProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Power-up -- {5}{W}{W}: Put a +1/+1 counter and an indestructible counter on Captain Marvel.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter"),
            new ManaCostsImpl<>("{5}{W}{W}")
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance())
            .setText("and an indestructible counter on {this}"));
        this.addAbility(ability);
    }

    private CaptainMarvelEarthsProtector(final CaptainMarvelEarthsProtector card) {
        super(card);
    }

    @Override
    public CaptainMarvelEarthsProtector copy() {
        return new CaptainMarvelEarthsProtector(this);
    }
}
