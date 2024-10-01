
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Sidorovich77
 */
public final class AlienInsectToken extends TokenImpl {

    public AlienInsectToken() {
        super("Alien Insect Token", "1/1 green and white Alien Insect creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setGreen(true);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private AlienInsectToken(final AlienInsectToken token) {
        super(token);
    }

    public AlienInsectToken copy() {
        return new AlienInsectToken(this);
    }
}
