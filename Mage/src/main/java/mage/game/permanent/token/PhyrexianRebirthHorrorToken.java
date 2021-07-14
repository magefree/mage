package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class PhyrexianRebirthHorrorToken extends TokenImpl {

    public PhyrexianRebirthHorrorToken(int power, int toughness) {
        super("Phyrexian Horror", "X/X colorless Phyrexian Horror artifact creature token");
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

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
