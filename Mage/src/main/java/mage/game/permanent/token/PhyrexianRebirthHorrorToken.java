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
        this(0, 0);
    }

    public PhyrexianRebirthHorrorToken(int power, int toughness) {
        super("Phyrexian Horror Token", "X/X colorless Phyrexian Horror artifact creature token");
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

        availableImageSetCodes = Arrays.asList("C16", "C18", "C19", "MBS", "CMR", "BRC", "ONC");
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
