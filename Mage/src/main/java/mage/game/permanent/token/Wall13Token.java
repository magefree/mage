package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Wall13Token extends TokenImpl {

    public Wall13Token() {
        super("Wall Token", "1/3 white Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(3);

        addAbility(DefenderAbility.getInstance());
    }

    private Wall13Token(final Wall13Token token) {
        super(token);
    }

    public Wall13Token copy() {
        return new Wall13Token(this);
    }
}
