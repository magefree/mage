
package mage.game.permanent.token;

import java.util.Collections;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author NinthWorld
 */
public final class TrooperToken2 extends TokenImpl {

    public TrooperToken2() {
        super("Trooper", "1/1 black Trooper creature token");
        availableImageSetCodes.addAll(Collections.singletonList("SWS"));
        this.setTokenType(2);

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TROOPER);

        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public TrooperToken2(final TrooperToken2 token) {
        super(token);
    }

    public TrooperToken2 copy() {
        return new TrooperToken2(this);
    }
}
