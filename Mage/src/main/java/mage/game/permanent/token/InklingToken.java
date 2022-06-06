package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class InklingToken extends TokenImpl {

    public InklingToken() {
        super("Inkling Token", "2/1 white and black Inkling creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.INKLING);
        power = new MageInt(2);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("STX", "CLB");
    }

    private InklingToken(final InklingToken token) {
        super(token);
    }

    @Override
    public InklingToken copy() {
        return new InklingToken(this);
    }
}
