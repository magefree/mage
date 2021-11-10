

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class AbhorrentOverlordHarpyToken extends TokenImpl {

    public AbhorrentOverlordHarpyToken() {
        super("Harpy Token", "1/1 black Harpy creature tokens with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HARPY);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }

    public AbhorrentOverlordHarpyToken(final AbhorrentOverlordHarpyToken token) {
        super(token);
    }

    public AbhorrentOverlordHarpyToken copy() {
        return new AbhorrentOverlordHarpyToken(this);
    }
}
