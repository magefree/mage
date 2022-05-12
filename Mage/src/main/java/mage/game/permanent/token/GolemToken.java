package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author North
 */
public final class GolemToken extends TokenImpl {

    public GolemToken() {
        super("Golem Token", "3/3 colorless Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("MM2", "NPH", "SOM", "MH1", "M20", "CMR", "2XM");
    }

    public GolemToken(final GolemToken token) {
        super(token);
    }

    public GolemToken copy() {
        return new GolemToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
