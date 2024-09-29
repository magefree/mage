package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SleepCursedFaerie extends CardImpl {

    public SleepCursedFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Sleep-Cursed Faerie enters the battlefield tapped with three stun counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with three stun counters on it"
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.STUN.createInstance(3)));
        this.addAbility(ability);

        // {1}{U}: Untap Sleep-Cursed Faerie.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{1}{U}")));
    }

    private SleepCursedFaerie(final SleepCursedFaerie card) {
        super(card);
    }

    @Override
    public SleepCursedFaerie copy() {
        return new SleepCursedFaerie(this);
    }
}
