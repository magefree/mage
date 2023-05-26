package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class TyranidToken extends TokenImpl {

    public TyranidToken() {
        super("Tyranid Token", "1/1 green Tyranid creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TYRANID);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public TyranidToken(final TyranidToken token) {
        super(token);
    }

    @Override
    public TyranidToken copy() {
        return new TyranidToken(this);
    }
}
