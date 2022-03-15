package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class WhiteBlackSpiritToken extends TokenImpl {

    public WhiteBlackSpiritToken() {
        super("Spirit Token", "1/1 white and black Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("MH1", "C21"));
    }

    public WhiteBlackSpiritToken(final WhiteBlackSpiritToken token) {
        super(token);
    }

    public WhiteBlackSpiritToken copy() {
        return new WhiteBlackSpiritToken(this);
    }
}
