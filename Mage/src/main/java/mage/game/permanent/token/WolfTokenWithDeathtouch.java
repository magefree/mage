package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class WolfTokenWithDeathtouch extends TokenImpl {

    public WolfTokenWithDeathtouch() {
        super("Wolf Token", "1/1 black Wolf creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(DeathtouchAbility.getInstance());
    }

    public WolfTokenWithDeathtouch(final WolfTokenWithDeathtouch token) {
        super(token);
    }

    public WolfTokenWithDeathtouch copy() {
        return new WolfTokenWithDeathtouch(this);
    }
}
