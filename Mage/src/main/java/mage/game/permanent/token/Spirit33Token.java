package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class Spirit33Token extends TokenImpl {

    public Spirit33Token() {
        super("Spirit Token", "3/3 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
    }

    private Spirit33Token(final Spirit33Token token) {
        super(token);
    }

    public Spirit33Token copy() {
        return new Spirit33Token(this);
    }
}
