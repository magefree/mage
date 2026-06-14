package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public class Hero32Token extends TokenImpl {

    public Hero32Token() {
        super("Hero Token", "3/2 white Hero creature token with vigilance");
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HERO);
        this.addAbility(VigilanceAbility.getInstance());
    }

    private Hero32Token(final Hero32Token token) { super(token); }

    public Hero32Token copy() { return new Hero32Token(this); }
}
