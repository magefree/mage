package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class IllusionVillainToken extends TokenImpl {

    public IllusionVillainToken() {
        super("Illusion Token", "3/3 blue Illusion Villain creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);

        subtype.add(SubType.ILLUSION);
        subtype.add(SubType.VILLAIN);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private IllusionVillainToken(final IllusionVillainToken token) {
        super(token);
    }

    public IllusionVillainToken copy() {
        return new IllusionVillainToken(this);
    }
}
