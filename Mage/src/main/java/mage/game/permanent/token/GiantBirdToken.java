package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ciaccona007
 */

public final class GiantBirdToken extends TokenImpl {

    public GiantBirdToken() {
        super("Giant Bird Token", "4/4 red Giant Bird creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.BIRD);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public GiantBirdToken(final GiantBirdToken token) {
        super(token);
    }

    public GiantBirdToken copy() {
        return new GiantBirdToken(this);
    }
}
