package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class OfficerToken extends TokenImpl {

    public OfficerToken() {
        super("Officer Token", "1/1 red Officer creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OFFICER);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private OfficerToken(final OfficerToken token) {
        super(token);
    }

    @Override
    public OfficerToken copy() {
        return new OfficerToken(this);
    }
}
