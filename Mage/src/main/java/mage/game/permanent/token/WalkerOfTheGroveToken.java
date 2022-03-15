package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class WalkerOfTheGroveToken extends TokenImpl {

    public WalkerOfTheGroveToken() {
        super("Elemental Token", "4/4 green Elemental creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("C13", "LRW", "MMA", "MOR");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("C13")) {
            this.setTokenType(2);
        }
    }

    public WalkerOfTheGroveToken(final WalkerOfTheGroveToken token) {
        super(token);
    }

    public WalkerOfTheGroveToken copy() {
        return new WalkerOfTheGroveToken(this);
    }
}
