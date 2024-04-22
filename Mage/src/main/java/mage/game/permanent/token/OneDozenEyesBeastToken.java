package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class OneDozenEyesBeastToken extends TokenImpl {

    public OneDozenEyesBeastToken() {
        super("Beast Token", "5/5 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    private OneDozenEyesBeastToken(final OneDozenEyesBeastToken token) {
        super(token);
    }

    public OneDozenEyesBeastToken copy() {
        return new OneDozenEyesBeastToken(this);
    }
}
