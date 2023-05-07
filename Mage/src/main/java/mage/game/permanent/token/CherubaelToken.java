package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author PurpleCrowbar
 */
public final class CherubaelToken extends TokenImpl {

    public CherubaelToken() {
        super("Cherubael", "Cherubael, a legendary 4/4 black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        addSuperType(SuperType.LEGENDARY);
        subtype.add(SubType.DEMON);
        color.setBlack(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
    }

    public CherubaelToken(final CherubaelToken token) {
        super(token);
    }

    public CherubaelToken copy() {
        return new CherubaelToken(this);
    }
}
