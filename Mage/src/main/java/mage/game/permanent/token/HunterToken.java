package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HunterToken extends TokenImpl {

    public HunterToken() {
        super("Hunter Token", "4/4 red Hunter creature token");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HUNTER);
    }

    public HunterToken(final HunterToken token) {
        super(token);
    }

    public HunterToken copy() {
        return new HunterToken(this);
    }
}

