package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class InsectToken extends TokenImpl {

    public InsectToken() {
        this((String) null);
    }

    public InsectToken(String setCode) {
        super("Insect Token", "1/1 green Insect creature token");
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("M10", "MM2", "SOI", "ZNR", "VOW", "NCC", "PCA");
    }

    public InsectToken(final InsectToken token) {
        super(token);
    }

    public InsectToken copy() {
        return new InsectToken(this);
    }
}