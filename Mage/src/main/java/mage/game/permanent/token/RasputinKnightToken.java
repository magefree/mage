package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class RasputinKnightToken extends TokenImpl {

    public RasputinKnightToken() {
        super("Knight Token", "2/2 white Knight creature token with protection from red");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        setOriginalExpansionSetCode("DMC");
    }

    public RasputinKnightToken(final RasputinKnightToken token) {
        super(token);
    }

    public RasputinKnightToken copy() {
        return new RasputinKnightToken(this);
    }
}
