package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OozeTrampleToken extends TokenImpl {

    public OozeTrampleToken() {
        super("Ooze Token", "0/0 green Ooze creature token with trample");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(TrampleAbility.getInstance());
    }

    private OozeTrampleToken(final OozeTrampleToken token) {
        super(token);
    }

    public OozeTrampleToken copy() {
        return new OozeTrampleToken(this);
    }
}
