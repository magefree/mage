package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WoodToken extends TokenImpl {

    public WoodToken() {
        super("Wood", "0/1 green Wall creature token with defender named Wood");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
    }

    public WoodToken(final WoodToken token) {
        super(token);
    }

    public WoodToken copy() {
        return new WoodToken(this);
    }
}
