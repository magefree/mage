package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * This token is supposed to be "gold" but the game rules don't support gold as a color in black border
 *
 * @author Saga
 */
public final class DragonTokenGold extends TokenImpl {

    public DragonTokenGold() {
        super("Dragon Token", "4/4 gold Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    protected DragonTokenGold(final DragonTokenGold token) {
        super(token);
    }

    public DragonTokenGold copy() {
        return new DragonTokenGold(this);
    }
}
