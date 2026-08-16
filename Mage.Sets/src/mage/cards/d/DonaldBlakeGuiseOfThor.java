package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DonaldBlakeGuiseOfThor extends CardImpl {

    public DonaldBlakeGuiseOfThor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Power-up -- {4}{W}{W}: Put two +1/+1 counters and a flying counter on Donald Blake. He becomes a God Warrior Hero.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
                .setText("put two +1/+1 counters"),
            new ManaCostsImpl<>("{4}{W}{W}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.FLYING.createInstance()).setText("and a flying counter on {this}."));
        ability.addEffect(new AddCardSubTypeSourceEffect(Duration.Custom, SubType.GOD, SubType.WARRIOR, SubType.HERO)
            .setText("He becomes a God Warrior Hero"));
        this.addAbility(ability);
    }

    private DonaldBlakeGuiseOfThor(final DonaldBlakeGuiseOfThor card) {
        super(card);
    }

    @Override
    public DonaldBlakeGuiseOfThor copy() {
        return new DonaldBlakeGuiseOfThor(this);
    }
}
