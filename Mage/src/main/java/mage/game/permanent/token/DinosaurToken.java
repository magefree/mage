package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DinosaurToken extends TokenImpl {

    public DinosaurToken() {
        super("Dinosaur Token", "3/3 green Dinosaur creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(TrampleAbility.getInstance());
    }

    private DinosaurToken(final DinosaurToken token) {
        super(token);
    }

    public DinosaurToken copy() {
        return new DinosaurToken(this);
    }
}
