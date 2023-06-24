package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class CarnivoreToken extends TokenImpl {

    public CarnivoreToken() {
        super("Carnivore Token", "3/1 red Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    public CarnivoreToken(final CarnivoreToken token) {
        super(token);
    }

    public CarnivoreToken copy() {
        return new CarnivoreToken(this);
    }
}
