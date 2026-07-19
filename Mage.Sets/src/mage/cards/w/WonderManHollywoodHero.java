package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author anonymous
 */
public final class WonderManHollywoodHero extends CardImpl {

    public WonderManHollywoodHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PERFORMER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each power-up ability of permanents you control can be activated an additional time.
        this.addAbility(PowerUpAbility.makePowerUpMoreAbility());

        // Power-up -- {5}{R}{R}: Put two +1/+1 counters on Wonder Man.
        this.addAbility(new PowerUpAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                new ManaCostsImpl<>("{5}{R}{R}")
        ));
    }

    private WonderManHollywoodHero(final WonderManHollywoodHero card) {
        super(card);
    }

    @Override
    public WonderManHollywoodHero copy() {
        return new WonderManHollywoodHero(this);
    }
}
