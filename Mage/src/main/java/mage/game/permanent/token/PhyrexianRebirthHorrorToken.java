package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class PhyrexianRebirthHorrorToken extends TokenImpl {

    public PhyrexianRebirthHorrorToken() {
        super("Horror", "X/X colorless Horror artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HORROR);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("C18", "C19", "MBS", "CMR");
    }

    public PhyrexianRebirthHorrorToken(final PhyrexianRebirthHorrorToken token) {
        super(token);
    }

    public PhyrexianRebirthHorrorToken copy() {
        return new PhyrexianRebirthHorrorToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
