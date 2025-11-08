package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class Demon66Token extends TokenImpl {

    public Demon66Token() {
        super("Demon Token", "6/6 black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
    }

    private Demon66Token(final Demon66Token token) {
        super(token);
    }

    @Override
    public Demon66Token copy() {
        return new Demon66Token(this);
    }
}
