package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WandOfTheElementsSecondToken extends TokenImpl {

    public WandOfTheElementsSecondToken() {
        super("Elemental Token", "3/3 red Elemental creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public WandOfTheElementsSecondToken(final WandOfTheElementsSecondToken token) {
        super(token);
    }

    public WandOfTheElementsSecondToken copy() {
        return new WandOfTheElementsSecondToken(this);
    }
}
