package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DinosaurXXToken extends TokenImpl {

    public DinosaurXXToken() {
        this(0);
    }

    public DinosaurXXToken(int xValue) {
        super("Dinosaur Token", "X/X green Dinosaur creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(TrampleAbility.getInstance());
    }

    private DinosaurXXToken(final DinosaurXXToken token) {
        super(token);
    }

    public DinosaurXXToken copy() {
        return new DinosaurXXToken(this);
    }
}
