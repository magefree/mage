

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class MonasteryMentorToken extends TokenImpl {

    public MonasteryMentorToken() {
        super("Monk Token", "1/1 white Monk creature token with prowess");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.MONK);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(new ProwessAbility());

        availableImageSetCodes = Arrays.asList("FRF", "2X2");
    }

    public MonasteryMentorToken(final MonasteryMentorToken token) {
        super(token);
    }

    public MonasteryMentorToken copy() {
        return new MonasteryMentorToken(this);
    }
}
