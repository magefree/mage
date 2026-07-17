package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class BlackAndRedGoblinToken extends TokenImpl {

    public BlackAndRedGoblinToken() {
        super("Goblin Token", "1/1 black and red Goblin creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOBLIN);
        color.setBlack(true);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private BlackAndRedGoblinToken(final BlackAndRedGoblinToken token) {
        super(token);
    }

    public BlackAndRedGoblinToken copy() {
        return new BlackAndRedGoblinToken(this);
    }
}
