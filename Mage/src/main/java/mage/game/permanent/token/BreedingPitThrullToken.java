package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class BreedingPitThrullToken extends TokenImpl {

    public BreedingPitThrullToken() {
        super("Thrull Token", "0/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THRULL);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("CMR", "DDC");
    }

    public BreedingPitThrullToken(final BreedingPitThrullToken token) {
        super(token);
    }

    public BreedingPitThrullToken copy() {
        return new BreedingPitThrullToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
