package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author Susucr
 */
public final class BeauToken extends TokenImpl {

    public BeauToken() {
        super("Beau", "Beau, a legendary blue Ox creature token with "
                + "\"This creature's power and toughness are each equal to the number of lands you control.\"");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OX);

        this.color.setBlue(true);
        
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(
                LandsYouControlCount.instance, Duration.EndOfGame
        ).setText("this creature's power and toughness are each equal to the number of lands you control")));
    }

    private BeauToken(final BeauToken token) {
        super(token);
    }

    public BeauToken copy() {
        return new BeauToken(this);
    }

}
