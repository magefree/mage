package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Angel33Token extends TokenImpl {

    public Angel33Token() {
        super("Angel Token", "3/3 white Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR");
    }

    public Angel33Token(final Angel33Token token) {
        super(token);
    }

    public Angel33Token copy() {
        return new Angel33Token(this);
    }
}
