package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 * @author muz
 */
public final class ZabuToken extends TokenImpl {

    public ZabuToken() {
        super("Zabu", "Zabu, a legendary green Cat creature token with "
            + "\"Landfall -- Whenever a land you control enters the battlefield, put a +1/+1 counter on Zabu.\"");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);

        this.color.setGreen(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private ZabuToken(final ZabuToken token) {
        super(token);
    }

    public ZabuToken copy() {
        return new ZabuToken(this);
    }
}
