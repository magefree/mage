package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DinosaurBeastToken extends TokenImpl {

    public DinosaurBeastToken() {
        this(0);
    }

    public DinosaurBeastToken(int xValue) {
        super("Dinosaur Beast Token", "X/X green Dinosaur Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.BEAST);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(TrampleAbility.getInstance());
    }

    private DinosaurBeastToken(final DinosaurBeastToken token) {
        super(token);
    }

    @Override
    public DinosaurBeastToken copy() {
        return new DinosaurBeastToken(this);
    }
}
