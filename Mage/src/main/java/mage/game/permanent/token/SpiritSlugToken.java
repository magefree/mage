package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class SpiritSlugToken extends TokenImpl {

    public SpiritSlugToken() {
        super("Spirit Slug Token", "1/1 Black Spirit Slug");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        subtype.add(SubType.SLUG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private SpiritSlugToken(final SpiritSlugToken token) {
        super(token);
    }

    public SpiritSlugToken copy() {
        return new SpiritSlugToken(this);
    }
}
