package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class HarpyToken extends TokenImpl {

    public HarpyToken() {
        super("Harpy Token", "1/1 black Harpy creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HARPY);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }

    public HarpyToken(final HarpyToken token) {
        super(token);
    }

    public HarpyToken copy() {
        return new HarpyToken(this);
    }
}
