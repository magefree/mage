
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class DinosaurBeastToken extends TokenImpl {

    public DinosaurBeastToken(int xValue) {
        super("Dinosaur Beast", "X/X green Dinosaur Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.BEAST);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(TrampleAbility.getInstance());
    }

    public DinosaurBeastToken(final DinosaurBeastToken token) {
        super(token);
    }

    public DinosaurBeastToken copy() {
        return new DinosaurBeastToken(this);
    }
}
