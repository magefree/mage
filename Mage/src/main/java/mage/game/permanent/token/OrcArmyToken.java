package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OrcArmyToken extends TokenImpl {

    public OrcArmyToken() {
        super("Orc Army Token", "0/0 black Orc Army creature token");

        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ORC);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private OrcArmyToken(final OrcArmyToken token) {
        super(token);
    }

    @Override
    public OrcArmyToken copy() {
        return new OrcArmyToken(this);
    }
}
