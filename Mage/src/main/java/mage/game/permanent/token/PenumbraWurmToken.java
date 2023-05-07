package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class PenumbraWurmToken extends TokenImpl {

    public PenumbraWurmToken() {
        super("Wurm Token", "6/6 black Wurm creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WURM);
        power = new MageInt(6);
        toughness = new MageInt(6);

        this.addAbility(TrampleAbility.getInstance());
    }

    public PenumbraWurmToken(final PenumbraWurmToken token) {
        super(token);
    }

    public PenumbraWurmToken copy() {
        return new PenumbraWurmToken(this);
    }
}
