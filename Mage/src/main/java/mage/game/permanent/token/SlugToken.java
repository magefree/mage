package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SlugToken extends TokenImpl {

    public SlugToken() {
        super("Slug Token", "1/1 black Slug creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SLUG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SlugToken(final SlugToken token) {
        super(token);
    }

    public SlugToken copy() {
        return new SlugToken(this);
    }
}
