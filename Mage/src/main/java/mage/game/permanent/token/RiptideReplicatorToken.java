package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RiptideReplicatorToken extends TokenImpl {

    public RiptideReplicatorToken(ObjectColor color, SubType type, int x) {
        super(type.getDescription() + " Token", "X/X creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        this.color.setColor(color);
        subtype.add(type);
        power = new MageInt(x);
        toughness = new MageInt(x);
    }

    public RiptideReplicatorToken(final RiptideReplicatorToken token) {
        super(token);
    }

    public RiptideReplicatorToken copy() {
        return new RiptideReplicatorToken(this);
    }
}
