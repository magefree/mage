package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Saga
 */
public final class DragonTokenGold extends TokenImpl {

    public DragonTokenGold() {
        super("Dragon Token", "4/4 gold Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setGold(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonTokenGold(final DragonTokenGold token) {
        super(token);
    }

    public DragonTokenGold copy() {
        return new DragonTokenGold(this);
    }
}