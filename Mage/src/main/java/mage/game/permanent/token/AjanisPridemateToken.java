package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 * @author LoneFox
 */
public final class AjanisPridemateToken extends TokenImpl {

    public AjanisPridemateToken() {
        super("Ajani's Pridemate", "2/2 white Cat Soldier creature token named Ajani's Pridemate with \"Whenever you gain life, put a +1/+1 counter on Ajani's Pridemate.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    public AjanisPridemateToken(final AjanisPridemateToken token) {
        super(token);
    }

    public AjanisPridemateToken copy() {
        return new AjanisPridemateToken(this);
    }

}
