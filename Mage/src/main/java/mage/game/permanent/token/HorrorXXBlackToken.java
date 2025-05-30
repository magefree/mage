package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HorrorXXBlackToken extends TokenImpl {

    public HorrorXXBlackToken() {
        this(1);
    }

    public HorrorXXBlackToken(int xValue) {
        super("Horror Token", "X/X black Horror creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private HorrorXXBlackToken(final HorrorXXBlackToken token) {
        super(token);
    }

    public HorrorXXBlackToken copy() {
        return new HorrorXXBlackToken(this);
    }
}
