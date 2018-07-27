

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author NinthWorld
 */
public final class ProtossToken extends TokenImpl {

    public ProtossToken() {
        super("Protoss", "3/3 blue Protoss creature token", 3, 3);
        this.setOriginalExpansionSetCode("DDSC");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.PROTOSS);
    }

    public ProtossToken(final ProtossToken token) {
        super(token);
    }

    @Override
    public ProtossToken copy() {
        return new ProtossToken(this);
    }
}
