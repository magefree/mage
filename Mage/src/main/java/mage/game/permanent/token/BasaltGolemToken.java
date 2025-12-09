package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author lagdotcom
 */
public final class BasaltGolemToken extends TokenImpl {

    public BasaltGolemToken() {
        super("Wall Token", "0/2 colorless Wall artifact creature token with defender");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(2);
        addAbility(DefenderAbility.getInstance());
    }

    private BasaltGolemToken(final BasaltGolemToken token) {
        super(token);
    }

    public BasaltGolemToken copy() {
        return new BasaltGolemToken(this);
    }
}
