

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class HunterToken extends TokenImpl {

    public HunterToken() {
        super("Hunter Token", "4/4 red Hunter creature token", 4, 4);
        this.setOriginalExpansionSetCode("SWS");
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

