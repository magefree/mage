package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class TyranidToken extends TokenImpl {

    public TyranidToken() {
        super("Tyranid Token", "5/5 green Tyranid creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TYRANID);
        power = new MageInt(5);
        toughness = new MageInt(5);

        availableImageSetCodes.addAll(Arrays.asList("40K"));
    }

    public TyranidToken(final TyranidToken token) {
        super(token);
    }

    @Override
    public TyranidToken copy() {
        return new TyranidToken(this);
    }
}
