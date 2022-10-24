package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpawnToken extends TokenImpl {

    public SpawnToken() {
        super("Spawn Token", "3/3 red Spawn creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        this.subtype.add(SubType.SPAWN);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public SpawnToken(final SpawnToken token) {
        super(token);
    }

    public SpawnToken copy() {
        return new SpawnToken(this);
    }
}
