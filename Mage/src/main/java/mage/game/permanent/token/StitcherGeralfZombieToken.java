package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class StitcherGeralfZombieToken extends TokenImpl {

    public StitcherGeralfZombieToken() {
        this(1);
    }

    public StitcherGeralfZombieToken(int xValue) {
        super("Zombie", "X/X blue Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        availableImageSetCodes = Arrays.asList("C14", "MIC", "VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode().equals("MIC")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode().equals("VOW")) {
            this.setTokenType(2);
        }
    }

    public StitcherGeralfZombieToken(final StitcherGeralfZombieToken token) {
        super(token);
    }

    public StitcherGeralfZombieToken copy() {
        return new StitcherGeralfZombieToken(this);
    }
}
